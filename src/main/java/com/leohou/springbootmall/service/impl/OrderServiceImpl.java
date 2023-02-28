package com.leohou.springbootmall.service.impl;

import com.leohou.springbootmall.dao.OrderDao;
import com.leohou.springbootmall.dao.ProductDao;
import com.leohou.springbootmall.dto.BuyItem;
import com.leohou.springbootmall.dto.CreateOrderRequest;
import com.leohou.springbootmall.model.Order;
import com.leohou.springbootmall.model.OrderItem;
import com.leohou.springbootmall.model.Product;
import com.leohou.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

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

		List<OrderItem> orderItemList = new ArrayList<>();
		OrderItem orderItem = null;

		int totalAmount = 0;
		int amount = 0;
		Product product = null;

		for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			product = productDao.getProductById(buyItem.getProductId());

			amount = buyItem.getQuantity() * product.getPrice();

//			計算總價錢
			totalAmount += amount;

//			轉換 BuyItem to OrderItem
			orderItem = new OrderItem();
			orderItem.setProductId(buyItem.getProductId());
			orderItem.setQuantity(buyItem.getQuantity());
			orderItem.setAmount(amount);

			orderItemList.add(orderItem);
		}

//		創建訂單
		Integer orderId = orderDao.createOrder(userId, totalAmount);

		orderDao.createOrderItems(orderId, orderItemList);

		return orderId;
	}
}
