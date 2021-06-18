package com.example.bookbecho.models;

public class UserModel {
    String username;
    String uid;

    public UserModel(String username, String uid) {
        this.username = username;
        this.uid = uid;
    }

    public UserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
