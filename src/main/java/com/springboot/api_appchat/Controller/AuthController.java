package com.springboot.api_appchat.Controller;

import com.springboot.api_appchat.Dto.UserDto;
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
    public ResponseEntity<?> confirmSignUp(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authService.confirmSignUp(userDto));
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(authService.signIn(email, password));
    }

    @PostMapping("forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        return ResponseEntity.ok(authService.forgotPassword(email));
    }

    @PostMapping("confirm-forgot-password")
    public ResponseEntity<?> confirmForgotPassword(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authService.confirmForgotPassword(userDto));
    }
}
