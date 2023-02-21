package com.leohou.springbootmall.dao;

import com.leohou.springbootmall.dto.UserRegisterRequest;
import com.leohou.springbootmall.model.User;

public interface UserDao {

	User getUserById(Integer userId);

	User getUserByEmail(String email);

	Integer createUser(UserRegisterRequest userRegisterRequest);

}
