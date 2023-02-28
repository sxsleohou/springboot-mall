package com.leohou.springbootmall.service;

import com.leohou.springbootmall.dto.CreateOrderRequest;
import com.leohou.springbootmall.model.Order;

public interface OrderService {

	Order getOrderById(Integer orderId);

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
