package com.example.demo.service;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.utils.OrderItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductService productService;

    public Order get(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public List<OrderDTO> getAll() {
        List<OrderDTO> allOrders = new ArrayList<>();
        orderRepository.findAll().forEach(order -> allOrders.add(new OrderDTO(order)));
        return allOrders;
    }

    public Order add(List<OrderItemInfo> infos) {
        List<OrderItem> orderItems = new ArrayList<>();
        Order order = new Order();
        infos.forEach(orderItemInfo -> {
            Product product = productService.get(orderItemInfo.getProductId());
            OrderItem curItem = new OrderItem(product, orderItemInfo.getProductCount(), order);
            orderItems.add(curItem);
        });
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        return order;
    }
}
