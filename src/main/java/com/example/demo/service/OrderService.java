package com.example.demo.service;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.utils.OrderItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductService productService;

    public Order get(Long id) {
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

    public void update(Long id, OrderItemInfo info) {
        Optional<Order> seletedOrder = orderRepository.findById(id);
        if (seletedOrder.isPresent()) {
            List<OrderItem> orderItems = seletedOrder.get().getOrderItems();
            orderItems = orderItems.stream().map(orderItem -> {
                if (orderItem.getProduct().getId().equals(info.getProductId())) {
                    orderItem.setProductCount(info.getProductCount());
                }
                return orderItem;
            }).collect(Collectors.toList());
            seletedOrder.get().setOrderItems(orderItems);
            orderRepository.save(seletedOrder.get());
        }

    }

    public Order addOrderItem(Long id, Long productId) {
        Optional<Order> seletedOrder = orderRepository.findById(id);
        if (seletedOrder.isPresent()) {
            List<OrderItem> orderItems = seletedOrder.get().getOrderItems();
            if (orderItems.stream().filter(orderItem -> orderItem.getProduct().getId().equals(productId)).toArray().length != 0) {
                orderItems = orderItems.stream().map(orderItem -> {
                    if (orderItem.getProduct().getId().equals(productId)) {
                        orderItem.setProductCount(orderItem.getProductCount() + 1);
                    }
                    return orderItem;
                }).collect(Collectors.toList());
            }
            else{
                Product product = productService.get(productId);
                OrderItem curItem = new OrderItem(product, 1, seletedOrder.get());
                orderItems.add(curItem);
            }

            seletedOrder.get().setOrderItems(orderItems);
            orderRepository.save(seletedOrder.get());
        }
        return seletedOrder.orElse(null);
    }
}
