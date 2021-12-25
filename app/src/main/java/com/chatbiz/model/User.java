package com.chatbiz.model;

public class User {
    private String name;
    private String email;
    private String photoLink;
    private String token;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public String getToken() {
        return token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
