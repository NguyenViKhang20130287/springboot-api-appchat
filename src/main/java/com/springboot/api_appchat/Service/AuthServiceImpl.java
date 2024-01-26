package com.springboot.api_appchat.Service;

import com.springboot.api_appchat.Config.EmailConfig;
import com.springboot.api_appchat.Dto.UserSignUpDto;
import com.springboot.api_appchat.Entity.User;
import com.springboot.api_appchat.Repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthRepository authRepository;
    private EmailConfig emailConfig;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> mapOtp = new HashMap<>();

    @Autowired
    public AuthServiceImpl(AuthRepository authRepository, EmailConfig emailConfig, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.emailConfig = emailConfig;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateOtp(String email) {
        String otp = String.format("%04d", (int) (Math.random() * 1000000));
        mapOtp.put(email, otp);
        return otp;
    }

    private boolean checkEmailIsValid(String email) {
        return mapOtp.containsKey(email);
    }

    private void setTimeOutOtp(String email, int minutes) {
        new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(minutes);
                mapOtp.remove(email);
                System.out.println("3 Minutes finish.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public boolean validateOtp(String email, String enteredOtp) {
        String otp = mapOtp.get(email);
        return otp != null && otp.equals(enteredOtp);
    }

    public void clearOtp(String email) {
        mapOtp.remove(email);
        System.out.println("OTP email: " + email + " is clear!!!");
    }

    @Override
    public boolean checkEmailIsExist(String email) {
        User user = authRepository.findByEmail(email);
        return user != null;
    }

    private String getRole(User user) {
        String result = "";
        if (user.getIsAdmin() == 0) result = "ADMIN";
        else if (user.getIsAdmin() == 1) result = "CUSTOMER";
        return result;
    }

    @Override
    public ResponseEntity<?> signUp(String email) {
        if (checkEmailIsExist(email)) return new ResponseEntity<>("Email is exist !!!", HttpStatus.BAD_REQUEST);
        emailConfig.send("Sign Up", email, generateOtp(email));
        setTimeOutOtp(email, 3);
        return new ResponseEntity<>("OTP is sent", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> confirmSignUp(UserSignUpDto userSignUpDto) {
        if (!checkEmailIsValid(userSignUpDto.getEmail()) || mapOtp.get(userSignUpDto.getEmail()) == null)
            return new ResponseEntity<>("OTP has expired !!!", HttpStatus.BAD_REQUEST);
        if (!userSignUpDto.getOtp().equals(mapOtp.get(userSignUpDto.getEmail())))
            return new ResponseEntity<>("OTP incorrect !!!", HttpStatus.BAD_REQUEST);
        User user = new User();
        user.setUsername(userSignUpDto.getEmail());
        user.setEmail(userSignUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
        user.setAvatar("LINK");
        user.setIsAdmin(1);
        authRepository.save(user);
        System.out.println("Email: " + userSignUpDto.getEmail() + " successful");
        clearOtp(userSignUpDto.getEmail());
        return ResponseEntity.ok("Sign up successful");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password !!!");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(getRole(user))));
    }
}
