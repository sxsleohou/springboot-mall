package com.leohou.springbootmall.service.impl;

import com.leohou.springbootmall.dao.OrderDao;
import com.leohou.springbootmall.dao.ProductDao;
import com.leohou.springbootmall.dao.UserDao;
import com.leohou.springbootmall.dto.BuyItem;
import com.leohou.springbootmall.dto.CreateOrderRequest;
import com.leohou.springbootmall.dto.OrderQueryParams;
import com.leohou.springbootmall.model.Order;
import com.leohou.springbootmall.model.OrderItem;
import com.leohou.springbootmall.model.Product;
import com.leohou.springbootmall.model.User;
import com.leohou.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

	private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;

	@Override
	public Order getOrderById(Integer orderId) {
		Order order = orderDao.getOrderById(orderId);

		List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

		order.setOrderItemList(orderItemList);

		return order;
	}

	@Override
	public List<Order> getOrders(OrderQueryParams orderQueryParams) {
		List<Order> orderList = orderDao.getOrders(orderQueryParams);
		List<OrderItem> orderItemList = null;

		for (Order order : orderList) {
			orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

			order.setOrderItemList(orderItemList);
		}

		return orderList;
	}

	@Override
	public Integer countOrder(OrderQueryParams orderQueryParams) {
		return orderDao.countOrder(orderQueryParams);
	}

	@Transactional
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
//		檢查user是否存在
		User user = userDao.getUserById(userId);

		if (null == user) {
			log.warn("該 {} userId 不存在", userId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		List<OrderItem> orderItemList = new ArrayList<>();
		OrderItem orderItem = null;

		int totalAmount = 0;
		int amount = 0;
		Product product = null;

		for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			product = productDao.getProductById(buyItem.getProductId());

//			檢查 product 是否存在、庫存是否足夠
			if (null == product) {
				log.warn("商品 {} 不存在", buyItem.getProductId());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			} else if (product.getStock() < buyItem.getQuantity()) {
				log.warn("商品 {} 庫存數量不足，無法購買。剩餘庫存 {}，欲購買數量{}", buyItem.getProductId(), product.getStock()
						, buyItem.getQuantity());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}

//			扣除商品庫存
			productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

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
