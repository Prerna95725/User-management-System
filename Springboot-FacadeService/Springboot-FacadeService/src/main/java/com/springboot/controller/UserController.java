package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.client.UserServiceClient;
import com.springboot.entity.JwtRequest;
import com.springboot.entity.JwtResponse;
import com.springboot.payloads.UserDto;
import com.springboot.service.UserService;
import com.springboot.utility.JWTUtility;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@Autowired
	UserServiceClient feignServiceUtil;
	
	@GetMapping("/users")
	public List<UserDto> getAllUser() {
		return feignServiceUtil.getAllUser();
	}
	
	
	@GetMapping("/users/{userId}")
	public UserDto getSingleUser(@PathVariable("userId") Integer uid) {
		 return feignServiceUtil.getUserById(uid); 
		 }
	 

	@PostMapping("/users")
	public UserDto createUser( @RequestBody UserDto userDto) {
		return feignServiceUtil.createUser(userDto);
	}
	
	
	@PutMapping("/users/{userId}")
	public UserDto updateUser( @RequestBody UserDto userDto, @PathVariable("userId") Integer uid)
	{
		return feignServiceUtil.updateUser(userDto,uid);
	}
	
	@DeleteMapping("/users/{userId}")
	public Object deleteUser(@PathVariable("userId") Integer uid) {
		return feignServiceUtil.deleteUser(uid);
	}
	@GetMapping("/home")
	public String home()
	{
		return "hello";
	}
	
	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest)throws Exception {
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							jwtRequest.getUsername(),
							jwtRequest.getPassword()
							)
					);
					} catch(BadCredentialsException e) {
					throw new Exception("INVALID_CREDENTIAL", e);
		}
		
		final UserDetails userDetails
        = userService.loadUserByUsername(jwtRequest.getUsername());

          final String token =jwtUtility.generateToken(userDetails);

         return  new JwtResponse(token);
	}
	
}
