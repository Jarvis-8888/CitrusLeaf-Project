package com.example.service;

import com.example.model.Login;
import com.example.model.User;

public interface UserService {

	User registerUser(User user);

	User validateUser(Login login, Long userId) throws Exception;

	
	
	

}
