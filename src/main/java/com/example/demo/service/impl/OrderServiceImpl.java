package com.example.demo.service.impl;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.OrderDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.BuyItem;
import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderQueryParams;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.mysql.cj.log.Log;


@Component
public class OrderServiceImpl implements OrderService{
	
	private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	
	@Override
	public Integer countOrder(OrderQueryParams orderQueryParams) {
		
		return orderDao.countOrder(orderQueryParams);
		
	}


	@Override
	public List<Order> getOrders(OrderQueryParams orderQueryParams) {
		
		List<Order> orderList = orderDao.getOrders(orderQueryParams);
		
		for (Order order: orderList) {
			List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
			
			order.setOrderItemList(orderItemList);
		}
		
		return orderList;
	}


	@Override
	public Order getOrderById(Integer orderId) {
		
		Order order = orderDao.getOrderById(orderId);
		
		List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
		
		order.setOrderItemList(orderItemList);
		
		return order;
		
	}


	@Transactional
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
		User user =  userDao.getUserById(userId);
		
		if (user == null) {
			log.warn(" the userId {} is not exists", userId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		//訂單total花費
		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();
				
		for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());
			
			if (product == null) {
				log.warn("product {} isn't exist", buyItem.getProductId());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}else if (product.getstock() < buyItem.getQuantity()){
				log.warn("product {} is not in stock, not buy. remaining stock {}, want to buy {}",
						buyItem.getProductId(), product.getstock(), buyItem.getQuantity());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			
			productDao.updateStock(product.getProductId() ,product.getstock() - buyItem.getQuantity());
				
			//計算總價錢
			int amount = buyItem.getQuantity() * product.getPrice();
			totalAmount = totalAmount + amount;
			
			//buyitem transation orderItem
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(buyItem.getProductId());
			orderItem.setQuantity(buyItem.getQuantity());
			orderItem.setAmount(amount);
			
			orderItemList.add(orderItem);
			
		}
		
		
		//create order
		Integer orderId = orderDao.createOrder(userId, totalAmount);
		
		
		orderDao.createOrderItems(orderId, orderItemList);
		
		
		return orderId;
	}

}
