package com.example.bookbecho.models;

public class UserModel {
    String username;
    String uid;
    String collegeName;

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public UserModel(String username, String uid, String collegeName) {
        this.username = username;
        this.uid = uid;
        this.collegeName = collegeName;
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
