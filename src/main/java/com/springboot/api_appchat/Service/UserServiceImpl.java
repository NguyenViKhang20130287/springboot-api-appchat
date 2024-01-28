package com.springboot.api_appchat.Service;

import com.springboot.api_appchat.Dto.RoomChatDto;
import com.springboot.api_appchat.Entity.ChatRoom;
import com.springboot.api_appchat.Entity.User;
import com.springboot.api_appchat.Repository.AuthRepository;
import com.springboot.api_appchat.Repository.ChatRoomRepository;
import com.springboot.api_appchat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> findByDisplayName(String displayName) {
        User user = userRepository.findByDisplayNameIgnoreCase(displayName);
        if (user == null) return new ResponseEntity<>("No result !!!", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
