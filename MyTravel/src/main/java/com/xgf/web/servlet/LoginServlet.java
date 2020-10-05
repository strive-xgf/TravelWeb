package com.xgf.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgf.bean.Msg;
import com.xgf.bean.User;
import com.xgf.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//登录servlet
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = new UserService();
        HttpSession session = request.getSession();//获取session对象，里面主要是验证码和当前user
        Msg msg = new Msg();//提示信息


            //用户输入的验证码
            String inputCheckCode = request.getParameter("inputCheckCode");
            //从session中获取系统当前生成的验证码
            String verificationCode = (String) session.getAttribute("verificationCode");
            System.out.println("inputCheckCode : " + inputCheckCode + " ;\t verificationCode : " + verificationCode);

            //inputCheckCode 与 verificationCode
            //相同表示验证码不正确，将提示信息写到页面的错误提示
            if (inputCheckCode == null || !inputCheckCode.equalsIgnoreCase(verificationCode)) {
                //验证码不看大小写
                msg.setCode(-3);
                msg.setData("验证码输入出错，请重新输入验证码");

                //将字符串转换为json数据格式返回给浏览器
                String json = new ObjectMapper().writeValueAsString(msg);
                response.getWriter().println(json);
                return; //返回不继续执行
            }


            //获取请求参数
            Map<String, String[]> map = request.getParameterMap();
            //当前登录用户
            User loginUser = new User();
            try {
                //参1 javaBean 参2 map 封装bean对象
                BeanUtils.populate(loginUser, map);//将map里面所有的参数赋值给javaBean(login就是输入的username和password)
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //调用service处理参数，查询当前登录username和password是否存在
            int code = userService.login(loginUser);
            //响应给浏览器 ajax 是响应json给浏览器就可以
            msg.setCode(code);//设置code

            if (code == 1) {
                msg.setData("登录成功，欢迎您的使用Login Success");
                //将登录user保存到session中
                session.setAttribute("user", loginUser);
                //判断是否开启免登陆
                //登录成功且开启了十天免登陆  就要保存/覆盖cookie
                String ssh = request.getParameter("ssh");
                //从session中删除生成的验证码 不移除的话可能会覆盖新的验证码
                session.removeAttribute("verificationCode");
            } else if (code == -1) {
                msg.setData("您输入的用户名或密码错误，请重新输入Incorrect user name or password");

            } else if (code == -2) {
                msg.setData("您的账号还没有激活，请前往激活The account is activated");
            }

            //将字符串转成json数据格式，返回给浏览器显示提示信息msg
            String json = new ObjectMapper().writeValueAsString(msg);
            response.getWriter().println(json);
        }
    }

