package com.quiz.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {

    private Integer user_id;

    @NotBlank(message = "User name cannot be empty")
    private String username;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    private String password;

    private String gender;
    private String profile_image;
    private long contact;

    private String confrim_password;
}