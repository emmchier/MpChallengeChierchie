package com.example.emmchierchie.mpchallengechierchie.Model.Pojo;

public class Purchase {

    private Float amount;
    private PaymentMethods method;
    private Issuer bank;
    private PayerCost payerCost;

    public Purchase() {
        this.amount = 0.0f;
        this.method = null;
        this.bank = null;
        this.payerCost = null;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public PaymentMethods getMethod() {
        return method;
    }

    public void setMethod(PaymentMethods method) {
        this.method = method;
    }

    public Issuer getBank() {
        return bank;
    }

    public void setBank(Issuer bank) {
        this.bank = bank;
    }

    public PayerCost getPayerCost() {
        return payerCost;
    }

    public void setPayerCost(PayerCost payerCost) {
        this.payerCost = payerCost;
    }
}
