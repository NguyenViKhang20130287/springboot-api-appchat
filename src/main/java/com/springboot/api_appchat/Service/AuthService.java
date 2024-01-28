package com.springboot.api_appchat.Service;

import com.springboot.api_appchat.Dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService  extends UserDetailsService {
    boolean checkEmailIsExist(String email);
    ResponseEntity<?> signUp(String email);
    ResponseEntity<?> confirmSignUp(UserDto userDto);
    ResponseEntity<?> signIn(String email_username, String password);
    ResponseEntity<?> forgotPassword(String email);
    ResponseEntity<?> confirmForgotPassword(UserDto userDto);
}
