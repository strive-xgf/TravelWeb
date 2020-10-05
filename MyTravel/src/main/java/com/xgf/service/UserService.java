package com.xgf.service;


import com.xgf.bean.User;
import com.xgf.dao.UserDao;
import com.xgf.util.GetDaoUtils;

//user业务层
public class UserService {

    private static UserDao userDao = null;

    //登录用户
    public int login(User user) {
        userDao = GetDaoUtils.getMapper(UserDao.class);

        User u = userDao.getUserByUsernamePassword(user);
        System.out.println(" ======UserService====== "+ u );
        if(u == null){
            return -1;  //账号密码匹配找不到
        }else {
            //判断用户user是否激活
            if(u.getStatus().equals("Y")){
                return 1;   //账号密码正确，且已经激活
            }else {
                return -2;  //账号未激活
            }
        }
    }
}
