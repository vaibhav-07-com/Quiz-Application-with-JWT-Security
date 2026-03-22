package com.quiz.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.dto.UserLogindto;
import com.quiz.dto.UserRequestDto;
import com.quiz.dto.UserResponseDto;
import com.quiz.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService service;
	
	@Autowired
	public UserController(UserService service) {
		this.service=service;
	}
	
	@GetMapping("/")
	public List<UserResponseDto> getAllUser() {
		return this.service.getAllUser();
	}
	
	//Get User Profile
	@GetMapping("/{id}")
	public UserResponseDto getUserById(@PathVariable int id){
	    return service.getUserById(id);
	}
	
	//Change Password
	@PutMapping("/{id}/change-password")
	public ResponseEntity<String> changePassword(@PathVariable int id,
	        @RequestBody Map<String,String> passwordMap){

	    service.changePassword(
	            id,
	            passwordMap.get("oldPassword"),
	            passwordMap.get("newPassword")
	    );

	    return ResponseEntity.ok("Password Updated Successfully");
	}
	
//	//Get User Quiz History
//	@GetMapping("/{id}/results")
//	public List<ResultDto> getUserResults(@PathVariable int id){
//	    return service.getUserResults(id);
//	}
//	
//	//Leaderboard API
//	@GetMapping("/leaderboard")
//	public List<LeaderboardDto> getLeaderboard(){
//	    return service.getLeaderboard();
//	}
	
	@PutMapping("/{id}")
	public UserResponseDto updateUser(@PathVariable int id,@Valid@RequestBody UserRequestDto u) {
		return this.service.updateUser(id,u);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") int id){
		this.service.deleteUserById(id);
		return ResponseEntity.ok("USER DELETED SUCCESSFULLY");
	}
	
	@PostMapping("/register")
	public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto u) {
	    return service.registerUser(u);
	}
	
	@PostMapping("/login")
	public UserResponseDto loginUser(@Valid @RequestBody UserLogindto loginDto) {
	    return service.loginUser(loginDto);
	}
	
}
