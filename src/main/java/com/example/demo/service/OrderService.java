package com.example.demo.service;

import org.springframework.stereotype.Component;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.model.Order;

@Component
public interface OrderService {
	
	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
	
	Order getOrderById(Integer orderId);

}
