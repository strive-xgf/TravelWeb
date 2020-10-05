<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>途牛旅游网 - 登录页</title>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!--导入angularJS文件-->
    <script src="js/angular.min.js"></script>
    <!--导入jquery-->
    <script src="js/jquery-3.3.1.js"></script>


    <script type="text/javascript" >
        //检查用户名是否规范
        function checkUserName(){
            //获取输入框的值
            var username = $("#username").val();

            //正则表达式  定义一个规则，执行test方法，符合规则返回true，否则返回false
            var reg =  /^\w{8,20}$/ ;
            var flag = reg.test(username); //判断

            //如果符合要求，设置输入框边框是正常，否则设置红色
            if(flag){
                $("#username").css("border","");
            }else{
                $("#username").css("border","1px solid red");
            }
            //alert(flag)
            return flag;
        }
        //检查密码是否规范
        function checkPassword(){
            //判断密码输入框的值是否合法
            var username = $("#password").val();
            var reg =  /^\w{8,20}$/ ;

            var flag = reg.test(username); //判断
            if(flag){
                $("#password").css("border","");//无色框
            }else{
                $("#password").css("border","1px solid red");//红框
            }
            //alert(flag)
            return flag;
        }
        //页面加载执行函数
        $(function () {
            $("#errorMsg").html("");
            // 判断两个输入框架的是否格式正确
            $("#username").blur(checkUserName);//输入框失去焦点
            // 如果正确，使用ajax发送请求到servlet
            $("#password").blur(checkPassword);

            //登录判断，username、password是否符合注册规范，如果不符合报错(防止直接注入)
            $("#btn_login").click(function () {
                //alert("点击btn_login")
                //要求两个值正确，我们才做提交
                if(checkUserName()&&checkPassword()){
                    var username = $("#username").val()
                    var password = $("#password").val()
                    var inputCheckCode = $("#inputCheckCode").val()
                    //alert(username+password+inputCheckCode)
                    //写提交
                    $.ajax({
                        url:"LoginServlet",
                        async:true,
                        data:$("#loginForm").serialize(),
                        type:"post",
                        dataType:"json",
                        success:function (data) {
                            // alert(data)  {"code":1,"data":"登录成功"}
                            if(1 == data.code){
                                //跳转到主页 index.jsp
                                $("#errorMsg").html("");
                                window.location="index.jsp"
                            }else{
                                //显示在界面上
                                $("#errorMsg").html(data.data);
                            }
                        },
                        error:function () {
                            alert("服务器发生了错误Error")//比如找不到servlet
                        }
                    });
                }else{
                    $("#errorMsg").html("用户名或密码输入不规范，用户名密码错误");
                }
            })
        })

    </script>

</head>

<body>
<!--引入头部-->
<div id="header">

</div>
<!-- 头部 end -->
<section id="login_wrap">
    <div class="fullscreen-bg" style="background: url(images/login_bg.png);height: 532px;">

    </div>
    <div class="login-box">
        <div class="title">
            <img src="images/login_logo.png" alt="">
            <span>欢迎登录途牛旅游账户</span>
        </div>
        <div class="login_inner">

            <!--登录错误提示消息-->
            <div id="errorMsg" class="alert alert-danger" ></div>
            <form id="loginForm" action="" method="post" accept-charset="utf-8">
                <input type="hidden" name="action" value="login"/>
                <input id="username" name="username" type="text" placeholder="请输入账号">
                <input id="password" name="password" type="password" placeholder="请输入密码" autocomplete="off">
                <div class="verify">
                    <input name="inputCheckCode" type="text" placeholder="请输入验证码" autocomplete="off">
                    <span><img src="checkCodeServlet" alt="" onclick="changeCheckCode(this)"></span>
                    <script type="text/javascript">
                        //图片点击事件
                        function changeCheckCode(img) {
                            img.src="checkCodeServlet?"+new Date().getTime();//添加时间戳
                        }
                    </script>
                </div>

                <div class="submit_btn" >
                    <button id="btn_login" type="button">登录</button>
                    <div class="auto_login">
                        <%-- ssh : 免密码登录 --%>
                        <input type="checkbox" name="ssh" value="ssh" class="checkbox" style="margin-left: 10px">
                        <span >十天免登陆</span>
                    </div>
                </div>
            </form>
            <div class="reg" >没有账户？<a href="javascript:;">立即注册</a></div>
        </div>
    </div>
</section>
<!--引入尾部-->
<div id="footer">

</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery-1.11.0.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
<!--导入布局js，共享header和footer-->
<script type="text/javascript" src="js/include.js"></script>
</body>
</html>