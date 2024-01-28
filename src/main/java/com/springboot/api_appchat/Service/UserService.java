package com.springboot.api_appchat.Service;

import com.springboot.api_appchat.Dto.RoomChatDto;
import com.springboot.api_appchat.Entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> findByDisplayName(String displayName);
}
