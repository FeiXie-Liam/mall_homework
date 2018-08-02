package com.example.demo.service;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public Order get(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public List<OrderDTO> getAll() {
        List<OrderDTO> allOrders = new ArrayList<>();
        orderRepository.findAll().forEach(order -> allOrders.add(new OrderDTO(order)));
        return allOrders;
    }
}
