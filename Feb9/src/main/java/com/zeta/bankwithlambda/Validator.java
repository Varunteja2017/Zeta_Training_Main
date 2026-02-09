package com.zeta.bankwithlambda;


public class Validator  extends RuntimeException{  //POJO
    public static void validate(int amount) {
        if (amount<=0){
        throw new IllegalArgumentException("Amount should be non negative and non Zero");}
    }
}
