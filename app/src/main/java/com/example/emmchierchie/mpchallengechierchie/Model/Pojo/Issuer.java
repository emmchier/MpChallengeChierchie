package com.example.emmchierchie.mpchallengechierchie.Model.Pojo;

public class Issuer {

    private String id;
    private String name;
    private String secure_thumbnail;

    public Issuer(String issuerdId, String name, String secure_thumbnail) {
        this.id = issuerdId;
        this.name = name;
        this.secure_thumbnail = secure_thumbnail;
    }

    public String getIssuerdId() {
        return id;
    }

    public void setIssuerdId(String issuerdId) {
        this.id = issuerdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecure_thumbnail() {
        return secure_thumbnail;
    }

    public void setSecure_thumbnail(String secure_thumbnail) {
        this.secure_thumbnail = secure_thumbnail;
    }
}
