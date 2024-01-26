package com.springboot.api_appchat.Controller;

import com.springboot.api_appchat.Dto.UserSignUpDto;
import com.springboot.api_appchat.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestParam String email) {
        return ResponseEntity.ok(authService.signUp(email));
    }

    @PostMapping("confirm-sign-up")
    public ResponseEntity<?> confirmSignUp(@RequestBody UserSignUpDto userSignUpDto) {
        return ResponseEntity.ok(authService.confirmSignUp(userSignUpDto));
    }
}
