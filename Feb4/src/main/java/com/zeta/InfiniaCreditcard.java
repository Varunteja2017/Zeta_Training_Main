package com.zeta;

public class InfiniaCreditcard extends CreditCardBase implements ICreditcard {
    public InfiniaCreditcard(String customerName){
        super(customerName);
    }

    @Override
    public boolean transaction(MerchantAccount account, float amount) {
        return false;
    }

    @Override
    public boolean withdrawCash(float amount) {
        return false;
    }
}
