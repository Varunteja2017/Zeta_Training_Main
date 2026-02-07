package com.zeta;

public class EmeraldCreditCard extends CreditCardBase implements ICreditcard{
    public EmeraldCreditCard(String customerName){
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
