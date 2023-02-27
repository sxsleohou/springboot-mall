package com.leohou.springbootmall.service;

import com.leohou.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
