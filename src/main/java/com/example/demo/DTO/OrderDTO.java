package com.example.demo.DTO;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;

import java.util.List;

public class OrderDTO {
    private List<OrderItem> orderItems;
    private Double totalPrice = 0D;

    public OrderDTO(Order order) {
        this.orderItems = order.getOrderItems();
        order.getOrderItems().forEach(
                orderItem -> this.totalPrice += orderItem.getProduct().getPrice() * orderItem.getProductCount());
    }


    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
