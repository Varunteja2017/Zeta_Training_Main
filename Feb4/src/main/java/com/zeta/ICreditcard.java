package com.zeta;

public interface ICreditcard {
    boolean transaction(MerchantAccount account, float amount);
    boolean withdrawCash(float amount);
}
