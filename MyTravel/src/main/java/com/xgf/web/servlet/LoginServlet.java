package com.xgf.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgf.bean.Msg;
import com.xgf.bean.User;
import com.xgf.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = new UserService();

        //用户输入的验证码
        String inputCheckCode = request.getParameter("inputCheckCode");
        //从session中获取系统当前生成的验证码
        String verificationCode = (String) request.getSession().getAttribute("verificationCode");

        System.out.println(inputCheckCode);
        System.out.println(verificationCode);

        //从session中删除生成的验证码 不移除会覆盖
        request.getSession().removeAttribute("verificationCode");
        //inputCheckCode 与 verificationCode
        //相同表示验证码不正确，将提示信息写到页面的错误提示
        if(inputCheckCode == null || !inputCheckCode.equalsIgnoreCase(verificationCode)){
            //验证码不看大小写
            Msg msg = new Msg();
            msg.setCode(-3);
            msg.setData("验证码输入出错，登录失败");

            //将字符串转换为json数据格式返回给浏览器
            String json = new ObjectMapper().writeValueAsString(msg);
            response.getWriter().println(json);
            return; //返回不继续执行
        }


        //获取请求参数
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            //参1 javaBean 参2 map 封装bean对象
            BeanUtils.populate(user,map);//将map里面所有的参数赋值给javaBean(login就是输入的username和password)
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service处理参数
        int code = userService.login(user);
        //响应给浏览器 ajax 是响应json给浏览器就可以
        Msg msg = new Msg();//创建msg错误信息提示类
        msg.setCode(code);//设置code
        if(code == - 1){
            msg.setData("您输入的用户名或密码错误，请重新输入Incorrect user name or password");
        }else  if(code ==  1){
            msg.setData("登录成功，欢迎您的使用Login Success");
            //将登录user保存到session中
            request.getSession().setAttribute("user",user);
        }else  if(code ==  -2){
            msg.setData("您的账号还没有激活，请前往激活The account is activated");
        }
        //将字符串转成json数据格式
        String json =new ObjectMapper().writeValueAsString(msg);
        response.getWriter().println(json);
    }
}
