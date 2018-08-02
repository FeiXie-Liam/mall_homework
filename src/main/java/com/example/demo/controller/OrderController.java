package com.example.demo.controller;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Order order = orderService.get(id);
        if(order != null){
            return ResponseEntity.ok(new OrderDTO(order));
        }
        return ResponseEntity.notFound().build();
    }
}
