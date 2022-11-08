package com.crud_login_register.spring_login_register_demo.controller.bean;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class User {
    private int id;
    private int roleId;
    private String userName;
    private String password;
    private int gender_id;
    private int[] certificate_ids;
    private String joining_date;
    private int board_id;
    MultipartFile file;
    private String base64file;

    private int messageCode;
    private String message;

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //    public User() {}

    public String getBase64file() {
        return base64file;
    }

    public void setBase64file(String base64file) {
        this.base64file = base64file;
    }

    public int getId() {
        return id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getGender_id() {
        return gender_id;
    }

    public int[] getCertificate_ids() {
        return certificate_ids;
    }

    public String getJoining_date() {
        return joining_date;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }

    public void setCertificate_ids(int[] certificate_ids) {
        this.certificate_ids = certificate_ids;
    }

    public void setJoining_date(String joining_date) {
        this.joining_date = joining_date;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gender_id=" + gender_id +
                ", certificate_ids=" + Arrays.toString(certificate_ids) +
                ", joining_date='" + joining_date + '\'' +
                ", board_id=" + board_id +
                '}';
    }
}
