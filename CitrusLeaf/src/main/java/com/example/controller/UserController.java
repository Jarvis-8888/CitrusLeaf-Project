package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Login;
import com.example.model.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	// Registration of user
	
	@PostMapping("/register")
	public ResponseEntity<User> login(@RequestBody User user)
	{
		User registeredUser = this.userService.registerUser(user);
		return new ResponseEntity<User>(registeredUser,HttpStatus.CREATED);
	}

}
