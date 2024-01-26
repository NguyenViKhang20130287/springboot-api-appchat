package com.springboot.api_appchat.Service;

import com.springboot.api_appchat.Dto.UserSignUpDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService  extends UserDetailsService {
    boolean checkEmailIsExist(String email);
    ResponseEntity<?> signUp(String email);
    ResponseEntity<?> confirmSignUp(UserSignUpDto userSignUpDto);
}
