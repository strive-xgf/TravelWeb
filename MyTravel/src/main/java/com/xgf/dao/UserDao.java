package com.xgf.dao;

import com.xgf.bean.User;

public interface UserDao {
    //通过usernmae和password查询用户
    public User getUserByUsernamePassword(User user);
}
