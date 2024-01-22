package com.springboot.crud.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.crud.entities.User;
import com.springboot.crud.payloads.UserDto;
import com.springboot.crud.repositories.UserRepo;
import com.springboot.crud.services.impl.UserServiceImpl;

import antlr.Utils;

public class UserServiceTest {

	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserRepo userRepo;
	
	
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testCreateUser() {
		User user = new User();
		user.setEmail("rohit@gmail.com");
		user.setFirstName("rohit");
		user.setLastName("sharma");
		
		UserDto userDto=new UserDto(0, "rohit","sharma","rohit@gmail.com", null, null);
		
		when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
		
		UserDto userDto1=userServiceImpl.createUser(userDto);
		
		assertNotNull(userDto1);
		assertEquals("rohit",userDto1.getFirstName());
	}
	
	@Test
	void testGetUserById() {
		Integer userId = 1;
		User user = new User();
		user.setEmail("rohit@gmail.com");
		user.setFirstName("rohit");
		user.setLastName("sharma");
		user.setId(userId);
		
		//when(userRepo.findById(userId)).thenReturn(user );
		when(userRepo.findUserById(userId)).thenReturn(user);
		UserDto userDto=userServiceImpl.getUserById(userId);
		assertNotNull(userDto);
		assertEquals("rohit",userDto.getFirstName());
	}
	
	@Test
	void testGetUserById_UsernameNotFoundException() throws Exception
	{
	
		when(userRepo.findUserById(anyInt())).thenReturn(null);
		System.out.println(assertThrows(ResponseStatusException.class,() ->{
			userServiceImpl.getUserById(90);
	
		}));
	}
	

	
	@Test
	void testUpdateUser() {
		Integer userId=2;
		User user = new User();
		user.setEmail("rohit@gmail.com");
		user.setFirstName("rohit");
		user.setLastName("sharma");
		user.setId(userId);
		
		UserDto userDto=new UserDto(userId, "rohit","sharma","rohit@gmail.com", null, null);
		
		 when(userRepo.findUserById(anyInt())).thenReturn(user);
			
        when(userRepo.save(Mockito.any(User.class))).thenReturn(user);
        UserDto userDto1=userServiceImpl.updateUser(userDto,userId);
        
        assertNotNull(userDto1);
		assertEquals("rohit",userDto1.getFirstName());
	}
	

	
	@Test
	void testdeleteUser() {
		Integer userId = 1;
		User user = new User();
		user.setEmail("rohit@gmail.com");
		user.setFirstName("rohit");
		user.setLastName("sharma");
		user.setId(userId);
		
		when(userRepo.findUserById(anyInt())).thenReturn(user);
		 Mockito.doNothing().when(userRepo).deleteById(userId);
		 
			String result=userServiceImpl.deleteUser(userId);
			assertNotNull(result);
			assertEquals("success",result);
	}
	
	@Test
	void testdeleteUser_UsernameNotFoundException()
	{
	
		when(userRepo.findUserById(anyInt())).thenReturn(null);
		System.out.println(assertThrows(ResponseStatusException.class,() ->{
			userServiceImpl.deleteUser(2);
	
		}));
	}
	
}
