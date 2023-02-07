package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ApiException;
import com.example.exception.ResourceNotFoundException;
import com.example.jwt.config.CustomUserDetailService;
import com.example.jwt.config.JwtUtil;
import com.example.model.JwtRequest;
import com.example.model.JwtResponse;
import com.example.model.User;
import com.example.payload.ApiResponse;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class JwtController {

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	//Login checked using JWT Token
	
	@PostMapping("/login")
	public ResponseEntity<?> createToken(@RequestBody JwtRequest request) throws Exception {

			try {

				this.authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			} catch (UsernameNotFoundException e) {
				e.printStackTrace();
				throw new Exception("Bad credentials !!!");
			} catch (BadCredentialsException e) {
				e.printStackTrace();
				throw new Exception("Bad credentials !!!");
			}

			UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getUsername());

			String token = this.jwtUtil.generateToken(userDetails);
			JwtResponse response = new JwtResponse();
			response.setToken(token);

			return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
	}

}
