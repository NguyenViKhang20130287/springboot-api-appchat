package com.springboot.api_appchat.Service;

import com.springboot.api_appchat.Dto.RoomChatDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> findAllMyRoomsById(long userId);
    ResponseEntity<?> createRoomChat(RoomChatDto roomChatDto);
}
