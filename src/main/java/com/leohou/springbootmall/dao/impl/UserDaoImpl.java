package com.leohou.springbootmall.dao.impl;

import com.leohou.springbootmall.dao.UserDao;
import com.leohou.springbootmall.dto.UserRegisterRequest;
import com.leohou.springbootmall.model.User;
import com.leohou.springbootmall.rowmapper.UserRowMapper;
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
public class UserDaoImpl implements UserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Integer createUser(UserRegisterRequest userRegisterRequest) {
		String _sql = "Insert Into User(email, password, created_date, last_modified_date) " +
				"Values (:email, :password, :createdDate, :lastModifiedDate) ";

		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("email", userRegisterRequest.getEmail());
		_map.put("password", userRegisterRequest.getPassword());

		Date date = new Date();
		_map.put("createdDate", date);
		_map.put("lastModifiedDate", date);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(_sql, new MapSqlParameterSource(_map), keyHolder);

		int _userId = keyHolder.getKey().intValue();

		return _userId;
	}

	@Override
	public User getUserById(Integer userId) {
		String _sql = "Select user_id, email, password, created_date, last_modified_date " +
				"From User where user_id = :userId";

		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("userId", userId);

		List<User> userList = namedParameterJdbcTemplate.query(_sql, _map, new UserRowMapper());

		if (userList.size() > 0) {
			return userList.get(0);
		} else {
			return null;
		}
	}
}
