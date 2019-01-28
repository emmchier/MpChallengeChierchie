package com.example.emmchierchie.mpchallengechierchie.Model.Pojo;
import java.util.List;

public class Installment {

    private List<PayerCost> payer_costs;

    public Installment(List<PayerCost> payer_costs) {
        this.payer_costs = payer_costs;
    }

    public List<PayerCost> getPayer_costs() {
        return payer_costs;
    }

    public void setPayer_costs(List<PayerCost> payer_costs) {
        this.payer_costs = payer_costs;
    }
}
