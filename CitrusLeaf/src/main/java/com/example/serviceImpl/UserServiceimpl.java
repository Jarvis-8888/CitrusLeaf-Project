package com.example.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Login;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;

@Service
public class UserServiceimpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User registerUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		return this.userRepository.save(user);
	}

	@Override
	public User validateUser(Login login, Long userId) throws Exception {
		User user= null;
		try {
			user = this.userRepository.findById(userId).orElseThrow(()-> new Exception("user Not Found !!!"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String email = login.getEmail();
		String password = login.getPassword();
		
		if( !(user.getEmail().equals(email) && user.getPassword().equals(password)) )
		{
			throw new Exception("user Not Found !!!");
		}
		return user;
	}

	

}
