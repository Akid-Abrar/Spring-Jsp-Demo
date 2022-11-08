package com.crud_login_register.spring_login_register_demo.controller.bean;

public class ShowUser {
    private String userName;
    private String password;
    private String gender;
    private String certificates;
    private String joining_date;
    private String board;
    private String base64file;

    public String getBase64file() {
        return base64file;
    }

    public void setBase64file(String base64file) {
        this.base64file = base64file;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    public void setJoining_date(String joining_date) {
        this.joining_date = joining_date;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getCertificates() {
        return certificates;
    }

    public String getJoining_date() {
        return joining_date;
    }

    public String getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "ShowUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", certificates='" + certificates + '\'' +
                ", joining_date='" + joining_date + '\'' +
                ", board='" + board + '\'' +
                '}';
    }
}
