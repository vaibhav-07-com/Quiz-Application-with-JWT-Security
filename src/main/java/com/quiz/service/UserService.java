package com.quiz.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.quiz.dto.UserLogindto;
import com.quiz.dto.UserResponseDto;
import com.quiz.dto.UserRequestDto;
import com.quiz.model.User;
import com.quiz.repository.UserRepository;

@Service
public class UserService {

	private UserRepository repo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository repo) {
		this.repo=repo;
	}
	
	public List<UserResponseDto> getAllUser(){
		 List<UserResponseDto> responseList = new ArrayList<>();

		    List<User> userList = (List<User>) this.repo.findAll();

		    for (User user : userList) {

		        UserResponseDto dto = new UserResponseDto();

		        dto.setUser_id(user.getId());
		        dto.setUsername(user.getUsername());
		        dto.setEmail(user.getEmail());
		        dto.setGender(user.getGender());
		        dto.setProfile_image(user.getProfile_image());
		        dto.setContact(user.getContact());

		        responseList.add(dto);
		    }
		    return responseList;
	}
	
	public UserResponseDto registerUser(UserRequestDto dto) {

	    // 1️⃣ Confirm password check
	    if (!dto.getPassword().equals(dto.getConfrim_password())) {
	        throw new RuntimeException("Password and Confirm Password do not match");
	    }

	    // 2️⃣ Encode password
	    User user = new User();
	    user.setUsername(dto.getUsername());
	    user.setEmail(dto.getEmail());
	    user.setGender(dto.getGender());
	    user.setContact(dto.getContact());
	    user.setProfile_image(dto.getProfile_image());
	    user.setPassword(passwordEncoder.encode(dto.getPassword()));

	    // 3️⃣ Save user
	    User savedUser = repo.save(user);

	    // 4️⃣ Convert to Response DTO (no password)
	    UserResponseDto response = new UserResponseDto();
	    response.setUser_id(savedUser.getId());
	    response.setUsername(savedUser.getUsername());
	    response.setEmail(savedUser.getEmail());
	    response.setGender(savedUser.getGender());
	    response.setContact(savedUser.getContact());
	    response.setProfile_image(savedUser.getProfile_image());

	    return response;
	}
	
	public UserResponseDto updateUser(int id, UserRequestDto dto) {

	    User existingUser = repo.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    existingUser.setUsername(dto.getUsername());
	    existingUser.setEmail(dto.getEmail());
	    existingUser.setGender(dto.getGender());
	    existingUser.setProfile_image(dto.getProfile_image());
	    existingUser.setContact(dto.getContact());

	    // Only update password if provided
	    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
	        existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
	    }

	    User updatedUser = this.repo.save(existingUser);

	    // Convert to Response DTO (SAFE)
	    UserResponseDto response = new UserResponseDto();
	    response.setUser_id(updatedUser.getId());
	    response.setUsername(updatedUser.getUsername());
	    response.setEmail(updatedUser.getEmail());
	    response.setGender(updatedUser.getGender());
	    response.setProfile_image(updatedUser.getProfile_image());
	    response.setContact(updatedUser.getContact());

	    return response;
	}
	
	public void deleteUserById(int id) {
		this.repo.deleteById(id);
	}
	
	public UserResponseDto loginUser(UserLogindto loginDto) {

	    // 1️⃣ Find user by email
	    User user = repo.findByEmail(loginDto.email())
	            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

	    // 2️⃣ Match password using encoder
	    if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
	        throw new RuntimeException("Invalid email or password");
	    }

	    // 3️⃣ Convert to Response DTO (no password)
	    UserResponseDto response = new UserResponseDto();
	    response.setUser_id(user.getId());
	    response.setUsername(user.getUsername());
	    response.setEmail(user.getEmail());
	    response.setGender(user.getGender());
	    response.setContact(user.getContact());
	    response.setProfile_image(user.getProfile_image());

	    return response;
	}

	public UserResponseDto getUserById(int id) {

	    // 1️⃣ Find user
	    User user = repo.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

	    // 2️⃣ Convert to Response DTO
	    UserResponseDto response = new UserResponseDto();
	    response.setUser_id(user.getId());
	    response.setUsername(user.getUsername());
	    response.setEmail(user.getEmail());
	    response.setGender(user.getGender());
	    response.setContact(user.getContact());
	    response.setProfile_image(user.getProfile_image());

	    // 3️⃣ Return response
	    return response;
	}
	
	public void changePassword(int id, String oldPassword, String newPassword) {

	    // 1️⃣ Get user
	    User user = repo.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // 2️⃣ Check old password
	    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
	        throw new RuntimeException("Old password is incorrect");
	    }

	    // 3️⃣ Encode new password
	    String encodedPassword = passwordEncoder.encode(newPassword);

	    // 4️⃣ Update password
	    user.setPassword(encodedPassword);

	    // 5️⃣ Save user
	    repo.save(user);
	}
}
