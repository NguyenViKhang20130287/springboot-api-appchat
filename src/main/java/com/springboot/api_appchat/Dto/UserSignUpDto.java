package com.springboot.api_appchat.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDto {
    private String email;
    private String password;
    private String otp;
}
