package com.lostred.ics.util;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC工具
 */
public class JdbcUtil {
    /**
     * JDBC单例对象
     */
    private static JdbcUtil INSTANCE;
    /**
     * 数据源
     */
    private final DruidDataSource ds;

    /**
     * 构造JDBC工具
     */
    private JdbcUtil() {
        Properties prop = new Properties();
        try {
            prop.load(JdbcUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String driver = prop.getProperty("driver");
        String url = prop.getProperty("url");
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");
        String maxActive = prop.getProperty("maxActive");
        //声明DruidDataSource
        ds = new DruidDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setMaxActive(Integer.parseInt(maxActive));
    }

    /**
     * 获取单例JDBC工具对象
     *
     * @return JDBC工具对象
     */
    public static JdbcUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (JdbcUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JdbcUtil();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取连接对象
     *
     * @return 连接对象
     */
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 归还连接
     *
     * @param conn 连接对象
     */
    public void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     *
     * @param ps 预编译对象
     * @param rs 结果集对象
     */
    public void release(PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
