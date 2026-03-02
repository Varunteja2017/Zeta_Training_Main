package com.zeta.basic_spring.services;

import com.zeta.basic_spring.repositor.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {
    @Autowired
    Repository repository;
    public  void  addOrder(){
        repository.saveOrder();
        System.out.println("Saved");

    }
}
