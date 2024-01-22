package com.springboot.crud.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.crud.payloads.UserDto;

import com.springboot.crud.services.UserService;


@WebMvcTest(value = UserController.class)

public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	 
	//@Autowired
	//private ObjectMapper objectMapper;
	
	
	

	
	ObjectMapper om = new ObjectMapper();
	
	@Test 
	void testCreateUser() throws Exception {
		UserDto userDto = new UserDto();
        userDto.setFirstName("Jugal");
        userDto.setLastName("Kapoor");
        userDto.setEmail("kapoor@gmail.com");
        userDto.setPassword("Jugal12uy");
        
        Mockito.when(userService.createUser(Mockito.any(UserDto.class))).thenReturn(userDto);
        //Mockito.doNothing().when(userService).createUser(userDto);
        String jsonRequest = om.writeValueAsString(userDto);
        
        ResultActions response = mockMvc.perform(post("/api/v1/users")
        		
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonRequest));
        
        response.andDo(print()).
        andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName",
                is(userDto.getFirstName())))
        .andExpect(jsonPath("$.lastName",
                is(userDto.getLastName())))
        .andExpect(jsonPath("$.email",
                is(userDto.getEmail())));

        
	}
	
	@Test
	void testUpdateUser() throws Exception{
		UserDto userDto = new UserDto();
		Integer id=2;
		userDto.setId(2);
        userDto.setFirstName("Jugal");
        userDto.setLastName("Kapoor");
        userDto.setEmail("kapoor@gmail.com");
        userDto.setPassword("Jugal1287");
        
        
        Mockito.when(userService.updateUser(Mockito.any(UserDto.class),Mockito.anyInt())).thenReturn(userDto);
        String jsonRequest = om.writeValueAsString(userDto);
        
        ResultActions response = mockMvc.perform(put("/api/v1/users/{userId}",id)
        		
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonRequest));
        
        response.andDo(print()).
        andExpect(status().isOk());
	}
	
	
	@Test
	 void testDeleteUser() throws Exception{
		Integer Id = 1;
		 Mockito.when(userService.deleteUser(Mockito.anyInt())).thenReturn("success");
		//Mockito.doNothing().when(userService).deleteUser(Id);
		 ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{userId}",Id));
		 response.andExpect(status().isOk())
         .andDo(print());
	}
	
	@Test
	void testgetUserById() throws Exception{
		UserDto userDto = new UserDto();
		Integer id=2;
		userDto.setId(2);
        userDto.setFirstName("Jugal");
        userDto.setLastName("Kapoor");
        userDto.setEmail("kapoor@gmail.com");
        userDto.setPassword("Jugal12");
        Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(userDto);
        
        ResultActions response = mockMvc.perform(get("/api/v1/users/{userId}",id));
        response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName", is(userDto.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(userDto.getLastName())))
        .andExpect(jsonPath("$.email", is(userDto.getEmail())));
	}
	@Test
	public void testGetAllUser() throws Exception {
		List<UserDto> listOfUser = new ArrayList<>();
		listOfUser.add(UserDto.builder().firstName("Rahul").lastName("Singh").email("rahul@gmail.com").password("ramesh").build());
		listOfUser.add(UserDto.builder().firstName("Priya").lastName("Devel").email("priya@gmail.com").password("devel").build());
		
		Mockito.when(userService.getAllUsers()).thenReturn(listOfUser);
		
		ResultActions response = mockMvc.perform(get("/api/v1/users"));
		
		
		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()",CoreMatchers.is(listOfUser.size())));
		
	}

	
	
	
}
