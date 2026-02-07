package com.zeta;
import java.util.logging.Logger;
public class Bank {
    public boolean transfer(Account sender,Account reciever,float amount){
        Logger logger = Logger.getLogger("Bank");
        try{
            sender.withdraw(amount);}
        catch (IllegalArgumentException IllegalArgumentException){
            logger.warning("Transfer failed: " + IllegalArgumentException.getMessage());
            return false;
        }   
        reciever.deposit(amount);
        return true;
        }
}

