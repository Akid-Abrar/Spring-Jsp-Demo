package com.crud_login_register.spring_login_register_demo.dao;

import com.crud_login_register.spring_login_register_demo.controller.bean.ShowUser;
import com.crud_login_register.spring_login_register_demo.controller.bean.User;
import com.crud_login_register.spring_login_register_demo.controller.bean.UserRegister;

import java.io.InputStream;
import java.util.List;

public interface UserDao {
    User getUserByName(String userName);
    int createNewUser(User user, InputStream inputStream);
    int updateUser(User user);
    int deleteUser(User user);
    List<User> getAllUsers();
    ShowUser showUserDetails(String userName);
    List<Integer> getCertificateIds(User user);

    int cerateNewRegisteredUser(UserRegister userRegister);
    List<UserRegister> getAllRegisteredUser();
    UserRegister getById(int id);
    int updateRegisteredUser(UserRegister userRegister);
    int deleteRegistereduser(int id);
    UserRegister getRegisteredUserByUserName(String userName);
}
