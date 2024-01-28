package com.springboot.api_appchat.Service;

import com.springboot.api_appchat.Dto.RoomChatDto;
import com.springboot.api_appchat.Entity.ChatRoom;
import com.springboot.api_appchat.Entity.User;
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
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ChatRoomRepository chatRoomRepository) {
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public ResponseEntity<?> findAllMyRoomsById(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return new ResponseEntity<>("User not found!!!", HttpStatus.NOT_FOUND);
        if (user.getMyRooms().isEmpty()) return new ResponseEntity<>("Your Room not found !!!", HttpStatus.OK);
        return new ResponseEntity<>(user.getMyRooms(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createRoomChat(RoomChatDto roomChatDto) {
        User user = userRepository.findById(roomChatDto.getHostId()).orElse(null);
        if (user == null) return new ResponseEntity<>("User not found!!!", HttpStatus.NOT_FOUND);
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(roomChatDto.getRoomName());
        chatRoom.setPasswordRoom(roomChatDto.getPasswordRoom());
        chatRoom.setHost(user);
        chatRoom.setCreatedAt(String.valueOf(LocalDateTime.now()));
        chatRoomRepository.save(chatRoom);
        return new ResponseEntity<>(chatRoom, HttpStatus.CREATED);
    }
}
