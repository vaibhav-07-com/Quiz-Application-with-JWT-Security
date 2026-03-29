package com.quiz.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDto {

    private Integer id;

    @NotBlank(message = "User name cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm password cannot be empty")
    private String confrim_password;

    private String gender;

    private String profile_image;

    @Pattern(regexp = "^[0-9]{10}$", message = "Contact must be a valid 10-digit number")
    private String contact;
}