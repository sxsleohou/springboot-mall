package com.leohou.springbootmall.dao;

import com.leohou.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
	Integer createOrder(Integer userId, int totalAmount);

	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
