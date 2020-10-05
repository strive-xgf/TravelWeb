package com.xgf.web.servlet;

import com.xgf.service.VerificationCodeService;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

//  验证码-生成验证码显示成图片的servlet
@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        //1：创建一个验证码的业务
        VerificationCodeService vcs = new VerificationCodeService();
        //2:生产一个随机的4个字符组成的字符串
        String verificationCode =  vcs.createRandomCode();
        System.out.println(verificationCode);

        //将验证码保存到session中
        HttpSession session = request.getSession();
        session.setAttribute("verificationCode",verificationCode);

//        request.setAttribute("verificationCode",verificationCode);

        //3:将字符串转成图片
        //BufferedImage类将图片生成到内存中，然后直接发送给浏览器
        BufferedImage image =  vcs.changeStringToImage(verificationCode);

        //4:使用OutputStream写到浏览器
//        System.out.println(image);
        //获取输出流
        ServletOutputStream outputStream = response.getOutputStream();

        ImageIO.write(image,"jpeg",outputStream);//参1，内存中的图片  参2，格式  参3，字节输出流
        outputStream.flush();
        outputStream.close();//关流
//        request.getRequestDispatcher("/").forward(request,response);
    }
}
