package com.springboot.crud.payloads;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.springboot.crud.entities.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class UserDto {

	 
	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "User name must be minimum of 4 character")
	private String firstName;
	@NotEmpty
	@Size(min = 4, message = "User name must be minimum of 4 character")
	private String lastName;
	@Email(message = "Email address is not valid")
	private String email;
	//@NotEmpty
	//@Size(min = 8, max =12, message = "Password must be min of 8 chars and max of 12")
	
	private String password;
	
	private Address address;
}
