package com.zeta.basic_spring.controller;

import com.zeta.basic_spring.entity.OrderRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @PostMapping
    OrderRequest addOrder(@Valid @RequestBody OrderRequest order){
        System.out.println(order);
        return order;
    }
    @GetMapping("/{id}")
    String getOrder(@PathVariable String id){
        return id;
    }
    @GetMapping
    String getAllOrders(){
        return "All Orders Displayed";
    }
    @DeleteMapping()
    String deleteOrder(){
        return "Deleted";
    }

}
