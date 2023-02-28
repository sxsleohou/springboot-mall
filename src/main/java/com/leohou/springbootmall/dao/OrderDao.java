package com.leohou.springbootmall.dao;

import com.leohou.springbootmall.model.Order;
import com.leohou.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

	Order getOrderById(Integer orderId);

	List<OrderItem> getOrderItemsByOrderId(Integer orderId);

	Integer createOrder(Integer userId, int totalAmount);

	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
