package com.leohou.springbootmall.service;

import com.leohou.springbootmall.dto.CreateOrderRequest;
import com.leohou.springbootmall.dto.OrderQueryParams;
import com.leohou.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

	Order getOrderById(Integer orderId);

	List<Order> getOrders(OrderQueryParams orderQueryParams);

	Integer countOrder(OrderQueryParams orderQueryParams);

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
