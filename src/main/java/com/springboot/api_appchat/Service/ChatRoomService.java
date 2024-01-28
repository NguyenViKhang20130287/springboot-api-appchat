package com.springboot.api_appchat.Service;

import com.springboot.api_appchat.Dto.RoomChatDto;
import org.springframework.http.ResponseEntity;

public interface ChatRoomService {
    ResponseEntity<?> findAllMyRoomsById(long userId);
    ResponseEntity<?> createRoomChat(RoomChatDto roomChatDto);
    ResponseEntity<?> deleteRoomChat(long userId, long chatRoomId);
    ResponseEntity<?> findRoomByName(String roomName);
    ResponseEntity<?> addUserToRoom(long roomId, long userId, long hostId);
}
