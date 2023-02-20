package com.leohou.springbootmall.service;

import com.leohou.springbootmall.dto.UserRegisterRequest;
import com.leohou.springbootmall.model.User;

public interface UserService {
	User getUserById(Integer userId);

	Integer register(UserRegisterRequest userRegisterRequest);
}
