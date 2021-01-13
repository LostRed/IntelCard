package com.lostred.ics.util;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.Dao;
import com.lostred.ics.annotation.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动对象注入工具
 */
public class WiredUtil {
    //类集合
    public static final Map<String, Class<?>> CONTROLLER_CLASS_MAP = new HashMap<>();
    public static final Map<String, Class<?>> SERVICE_CLASS_MAP = new HashMap<>();
    public static final Map<String, Class<?>> DAO_CLASS_MAP = new HashMap<>();
    //实例集合
    public static final Map<String, Object> CONTROLLER_INSTANCE_MAP = new HashMap<>();
    public static final Map<String, Object> SERVICE_INSTANCE_MAP = new HashMap<>();
    public static final Map<String, Object> DAO_INSTANCE_MAP = new HashMap<>();

    /**
     * 扫描class文件
     *
     * @param packName 包名
     * @throws ClassNotFoundException 类丢失异常
     * @throws IOException            IO异常
     * @throws IllegalAccessException 非法访问异常
     * @throws InstantiationException 新建实例异常
     */
    public static void scanClass(String packName) throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = WiredUtil.class.getClassLoader();
        String path = packName.replace(".", "/");
        URL url = classLoader.getResource(path);//path为src下的类路径,url为绝对路径
        String packPath = "";
        if (url != null) {
            packPath = url.getPath().replaceAll("%20", "");//解决路径中含有空格的情况
        }
        packPath = URLDecoder.decode(packPath, "utf-8"); //解决路径包含中文的情况
        File dir = new File(packPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.getName().contains("$") && file.isFile() && file.getName().endsWith(".class")) {
                    String className = packName + "." + file.getName().replace(".class", "");
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        push(clazz);
                    } else if (clazz.isAnnotationPresent(Service.class)) {
                        push(clazz);
                    } else if (clazz.isAnnotationPresent(Dao.class)) {
                        push(clazz);
                    }
                } else if (file.isDirectory()) {
                    String newPackName = packName + "." + file.getName();
                    scanClass(newPackName);
                }
            }
        }
    }

    /**
     * 新建实例并加入集合
     *
     * @param clazz 类对象
     * @throws IllegalAccessException 非法访问异常
     * @throws InstantiationException 新建实例异常
     */
    public static void push(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        if (clazz.isAnnotationPresent(Controller.class)) {
            CONTROLLER_CLASS_MAP.put(clazz.getAnnotation(Controller.class).value(), clazz);
            CONTROLLER_INSTANCE_MAP.put(clazz.getAnnotation(Controller.class).value(), clazz.newInstance());
        } else if (clazz.isAnnotationPresent(Service.class)) {
            SERVICE_CLASS_MAP.put(clazz.getAnnotation(Service.class).value(), clazz);
            SERVICE_INSTANCE_MAP.put(clazz.getAnnotation(Service.class).value(), clazz.newInstance());
        } else if (clazz.isAnnotationPresent(Dao.class)) {
            DAO_CLASS_MAP.put(clazz.getAnnotation(Dao.class).value(), clazz);
            DAO_INSTANCE_MAP.put(clazz.getAnnotation(Dao.class).value(), clazz.newInstance());
        }
    }

    /**
     * 将实例集合的键注入对象
     *
     * @throws ClassNotFoundException 类丢失异常
     * @throws IOException            IO异常
     * @throws IllegalAccessException 非法访问异常
     * @throws InstantiationException 新建实例异常
     */
    public static void wiredAll() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        for (String key : CONTROLLER_CLASS_MAP.keySet()) {
            wired(CONTROLLER_CLASS_MAP.get(key));
        }
        for (String key : SERVICE_CLASS_MAP.keySet()) {
            wired(SERVICE_CLASS_MAP.get(key));
        }
    }

    /**
     * 注入对象
     *
     * @param clazz 类对象
     * @param <T>   Controller、Service、Dao实现类
     * @throws IllegalAccessException 非法访问异常
     */
    public static <T> void wired(Class<T> clazz) throws IllegalAccessException {
        if (clazz.isAnnotationPresent(Controller.class)) {
            Field[] fields = clazz.getDeclaredFields();
            String key = clazz.getAnnotation(Controller.class).value();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object object = SERVICE_INSTANCE_MAP.get(field.getType().getSimpleName());
                    field.setAccessible(true);
                    field.set(CONTROLLER_INSTANCE_MAP.get(key), object);
                }
            }
        } else if (clazz.isAnnotationPresent(Service.class)) {
            Field[] fields = clazz.getDeclaredFields();
            String key = clazz.getAnnotation(Service.class).value();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object object = DAO_INSTANCE_MAP.get(field.getType().getSimpleName());
                    field.setAccessible(true);
                    field.set(SERVICE_INSTANCE_MAP.get(key), object);
                }
            }
        }
    }
}
