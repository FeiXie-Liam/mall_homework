package com.example.demo.controller;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.service.OrderService;
import com.example.demo.utils.OrderItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    public ResponseEntity add(@RequestBody List<OrderItemInfo> infos){
        Order order = orderService.add(infos);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody OrderItemInfo info){
        orderService.update(id, info);
        return ResponseEntity.noContent().build();
    }
}
