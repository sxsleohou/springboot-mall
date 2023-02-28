package com.leohou.springbootmall.dao.impl;

import com.leohou.springbootmall.dao.OrderDao;
import com.leohou.springbootmall.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Integer createOrder(Integer userId, int totalAmount) {
//		MySQL
		String _sql = "Insert Into `order`(user_id, total_amount, created_Date, last_modified_date) " +
				"Values (:userId, :totalAmount, :createdDate, :lastModifiedDate) ";

//      SQL Server
//		String _sql = "Insert Into [order](user_id, total_amount, created_Date, last_modified_date) " +
//				"Values (:userId, :totalAmount, :createdDate, :lastModifiedDate) ";

		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("userId", userId);
		_map.put("totalAmount", totalAmount);

		Date date = new Date();
		_map.put("createdDate", date);
		_map.put("lastModifiedDate", date);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(_sql, new MapSqlParameterSource(_map), keyHolder);

		int _productId = keyHolder.getKey().intValue();

		return _productId;
	}

	@Override
	public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

		String _sql = "Insert Into order_item(order_id, product_id, quantity, amount) " +
				"Values (:orderId, :productId, :quantity, :amount) ";

//		使用 for loop 加入，效率較低
//		Map<String, Object> _map = null;
//		for (OrderItem orderItem : orderItemList) {
//			_map = new HashMap<String, Object>();
//			_map.put("orderId", orderItem.getOrderId());
//			_map.put("productId", orderItem.getProductId());
//			_map.put("quantity", orderItem.getQuantity());
//			_map.put("amount", orderItem.getAmount());
//
//			namedParameterJdbcTemplate.update(_sql, _map);
//		}

//		使用 batchUpdate 一次性加入數據，效率較高
		MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];
		OrderItem orderItem = null;

		for (int _i = 0; _i < orderItemList.size(); _i++) {
			orderItem = orderItemList.get(_i);

			parameterSources[_i] = new MapSqlParameterSource();
			parameterSources[_i].addValue("orderId", orderId);
			parameterSources[_i].addValue("productId", orderItem.getProductId());
			parameterSources[_i].addValue("quantity", orderItem.getQuantity());
			parameterSources[_i].addValue("amount", orderItem.getAmount());
		}

		namedParameterJdbcTemplate.batchUpdate(_sql, parameterSources);
	}
}
