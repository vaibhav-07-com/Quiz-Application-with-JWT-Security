package com.quiz.dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Integer user_id;
    private String username;
    private String email;
    private String gender;
    private String profile_image;
    private long contact;
}