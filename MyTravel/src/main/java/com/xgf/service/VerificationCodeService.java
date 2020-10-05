package com.xgf.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/*
* 生成验证码，并将验证码转化为图片的业务层
* */
public class VerificationCodeService {

    public VerificationCodeService() {
    }

    // 生成验证码字符串 -- 随机数
    public String createRandomCode() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";//所有随机数字符串集合
        //Random类 产生指定范围内的随机数
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        //随机截取4个字符
        for(int i =0 ;i < 4; i++){//4个随机数
            //包括开头不包括结尾 从0到str.length()-1里面随机产生一个整数
            int index = random.nextInt(str.length());//从0到str.length()不包括最后一个 右边开区间
            char randomStr = str.charAt(index);//安装随机产生的值获取字符
            sb.append(randomStr);//StringBuilder拼接字符串

        }
        return sb.toString();
    }


    //  将生成的随机字符串验证码转化为图片
    public BufferedImage changeStringToImage(String code) {
        Random rd = new Random();
        //创建一个画布
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //创建画笔
        Graphics g = image.getGraphics();
        //给画笔设置颜色(绘制随机验证码的时候的验证码颜色)
        g.setColor(new Color(240,240,240));  //#00000   FFFFFF
        //设置验证码的 背景色
        g.fillRect(0, 0, width, height);
        // 设置字体
        g.setFont(new Font("宋体",Font.BOLD,16));

        g.setColor(new Color(0,0,0));  //#00000   FFFFFF
        // g.drawString(checkCodeStr, 20, 20);
        for (int i = 0; i <4 ; i++) {
            //画字符
            g.setColor(new Color(rd.nextInt(120),rd.nextInt(120),rd.nextInt(120)));
            g.drawString(code.charAt(i)+"", 16*i + rd.nextInt(16), 15 + rd.nextInt(10) );
            if(i % 2 == 0) {//画线
                g.setColor(new Color(rd.nextInt(120), rd.nextInt(120), rd.nextInt(120)));
                g.drawLine(rd.nextInt(75), rd.nextInt(28), rd.nextInt(75), rd.nextInt(28));
            }
        }
        return image;
    }
}
