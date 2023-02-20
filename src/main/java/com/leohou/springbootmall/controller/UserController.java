package com.leohou.springbootmall.controller;

import com.leohou.springbootmall.dto.UserRegisterRequest;
import com.leohou.springbootmall.model.User;
import com.leohou.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/users/register")
	public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
		Integer userId = userService.register(userRegisterRequest);

		User user = userService.getUserById(userId);

		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
}
