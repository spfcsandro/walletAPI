package com.wallet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.UserDTO;
import com.wallet.entity.User;
import com.wallet.response.Response;
import com.wallet.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<Response<UserDTO>> create(@Valid @RequestBody UserDTO userDTO, BindingResult result){
		
		Response<UserDTO> response = new Response<UserDTO>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		User user = userService.save(convertDtoToEntity(userDTO));
		response.setData(convertEntityToDto(user));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	private User convertDtoToEntity(UserDTO userDTO) {
		User u = new User();
		u.setId(userDTO.getId());
		u.setEmail(userDTO.getEmail());
		u.setName(userDTO.getName());
		u.setPassword(userDTO.getPassword());
		
		return u;
	}
	
	private UserDTO convertEntityToDto(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());
		userDTO.setPassword(user.getPassword());
		
		return userDTO;
	}
	
}
