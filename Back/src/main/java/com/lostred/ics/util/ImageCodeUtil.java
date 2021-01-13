package com.lostred.ics.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码图片工具
 */
public class ImageCodeUtil {
    private final int weight = 70;//验证码图片的长和宽
    private final int height = 35;
    private final Random random = new Random();//获取随机数对象
    private final String[] fontNames = {"Georgia"};
    private String text; //用来保存验证码的文本内容

    /**
     * 获取随机的颜色
     *
     * @return 颜色
     */
    private Color randomColor() {
        int r = this.random.nextInt(200);
        int g = this.random.nextInt(200);
        int b = this.random.nextInt(200);
        return new Color(r, g, b);
    }

    /**
     * 获取随机字体
     *
     * @return 字体
     */
    private Font randomFont() {
        int index = random.nextInt(fontNames.length);//获取随机的字体
        String fontName = fontNames[index];
        int style = random.nextInt(4);//随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
        int size = random.nextInt(5) + 20; //随机获取字体的大小
        return new Font(fontName, style, size);//返回一个随机的字体
    }

    /**
     * 获取随机字符
     *
     * @return 字符
     */
    private char randomChar() {
        String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        int index = random.nextInt(codes.length());
        return codes.charAt(index);
    }

    /**
     * 画干扰线，验证码干扰线用来防止计算机解析图片
     *
     * @param image 图片缓冲区
     */
    private void drawLine(BufferedImage image) {
        int num = random.nextInt(10); //定义干扰线的数量
        Graphics2D g = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = random.nextInt(weight);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(weight);
            int y2 = random.nextInt(height);
            g.setColor(randomColor());
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 创建图片的方法
     *
     * @return 图片缓冲区
     */
    private BufferedImage createImage() {
        //创建图片缓冲区
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        //设置背景色随机
        g.setColor(new Color(200, 200, 200));
        g.fillRect(0, 0, weight, height);
        //返回一个图片
        return image;
    }

    /**
     * 获取验证码图片的方法
     *
     * @return 获取图片
     */
    public BufferedImage getImage() {
        BufferedImage image = createImage();
        Graphics2D g = (Graphics2D) image.getGraphics(); //获取画笔
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String s = randomChar() + "";      //随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
            sb.append(s);                      //添加到StringBuilder里面
            float x = i * 1.0F * weight / 4;   //定义字符的x坐标
            g.setFont(randomFont());           //设置字体，随机
            g.setColor(randomColor());         //设置颜色，随机
            g.drawString(s, x, height - 5);
        }
        this.text = sb.toString();
        drawLine(image);
        return image;
    }

    /**
     * 获取验证码文本的方法
     *
     * @return 验证码文本
     */
    public String getText() {
        return text;
    }

    public void output(BufferedImage image, OutputStream out) throws IOException { //将验证码图片写出的方法
        ImageIO.write(image, "png", out);
    }
}
