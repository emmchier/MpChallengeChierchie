package com.example.emmchierchie.mpchallengechierchie.Model.Pojo;

public class PayerCost {

    private String recommended_message;
    private Double installment_amount;
    private Double total_amount;

    public PayerCost(String recommended_message, Double installment_amount, Double total_amount) {
        this.recommended_message = recommended_message;
        this.installment_amount = installment_amount;
        this.total_amount = total_amount;
    }

    public String getRecommended_message() {
        return recommended_message;
    }

    public void setRecommended_message(String recommended_message) {
        this.recommended_message = recommended_message;
    }

    public Double getInstallment_amount() {
        return installment_amount;
    }

    public void setInstallment_amount(Double installment_amount) {
        this.installment_amount = installment_amount;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }
}
