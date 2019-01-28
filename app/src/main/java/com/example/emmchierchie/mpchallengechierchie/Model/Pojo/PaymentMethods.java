package com.example.emmchierchie.mpchallengechierchie.Model.Pojo;

public class PaymentMethods {

    private String id;
    private String name;
    private String secure_thumbnail;
    private String payment_type_id;

    public PaymentMethods(String methodId, String name, String secure_thumbnail, String payment_type_id) {
        this.id = methodId;
        this.name = name;
        this.secure_thumbnail = secure_thumbnail;
        this.payment_type_id = payment_type_id;
    }

    public String getMethodId() {
        return id;
    }

    public void setMethodId(String methodId) {
        this.id = methodId;
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

    public String getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(String payment_type_id) {
        this.payment_type_id = payment_type_id;
    }
}
