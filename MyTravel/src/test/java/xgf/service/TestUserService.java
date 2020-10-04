package xgf.service;

import com.xgf.bean.User;
import com.xgf.service.UserService;
import org.junit.Test;

//测试user业务层
public class TestUserService {

/*
    private static ApplicationContext applicationContext = null;
    private static UserDao userDao = null;

    //静态代码块 只加载一次
    static {
        //加载配置文件
        applicationContext = new ClassPathXmlApplicationContext("com/xgf/config/applicationContext.xml");
        //获取bean的两种方式
        // 1.类名首字母小写
//        userDao = (UserDao) applicationContext.getBean("userDao");
        // 2.类.class
        userDao = (UserDao) applicationContext.getBean(UserDao.class);
    }
*/

    //测试登录
    @Test
    public void test01(){
        UserService userService = new UserService();

        //创建user数据
        User user = new User();
        user.setUsername("861221293@qq.com");
        user.setPassword("123456");
        //user.setStatus("Y");//用户激活

        //测试登录
        int code = userService.login(user);
        if(code == 1){
            System.out.println("登录成功");
        }else if(code == -1){
            System.out.println("用户名或密码错误");
        }else if(code == -2){
            System.out.println("用户未激活");
        }
    }
}
