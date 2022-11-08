package com.crud_login_register.spring_login_register_demo.service.impl;

import com.crud_login_register.spring_login_register_demo.controller.bean.ShowUser;
import com.crud_login_register.spring_login_register_demo.controller.bean.User;
import com.crud_login_register.spring_login_register_demo.controller.bean.UserRegister;
import com.crud_login_register.spring_login_register_demo.dao.UserDao;
import com.crud_login_register.spring_login_register_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User getUserByUserName(String userName) {
        User user = userDao.getUserByName(userName);
        return user;
    }

    @Override
    public int createNewUser(User user, InputStream inputStream) {
        return userDao.createNewUser(user,inputStream);
    }


    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public ShowUser showUserDetails(String userName) {
        return userDao.showUserDetails(userName);
    }

    public List<Integer> getCertificateIds(User user) {
        return userDao.getCertificateIds(user);
    }

    @Override
    public int cerateNewRegisteredUser(UserRegister userRegister) {
        return userDao.cerateNewRegisteredUser(userRegister);
    }

    @Override
    public List<UserRegister> getAllRegisteredUser() {
        return userDao.getAllRegisteredUser();
    }

    @Override
    public UserRegister getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public int updateRegisteredUser(UserRegister userRegister) {
        return userDao.updateRegisteredUser(userRegister);
    }

    @Override
    public int deleteRegistereduser(int id) {
        return userDao.deleteRegistereduser(id);
    }

    @Override
    public UserRegister getRegisteredUserByUserName(String userName) {
        return userDao.getRegisteredUserByUserName(userName);
    }
}
