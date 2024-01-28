package com.springboot.api_appchat.Controller;

import com.springboot.api_appchat.Dto.RoomChatDto;
import com.springboot.api_appchat.Entity.ChatRoom;
import com.springboot.api_appchat.Entity.User;
import com.springboot.api_appchat.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("my-rooms")
    public ResponseEntity<?> findHostedChatRoomsById(@RequestParam long userId) {
        return ResponseEntity.ok(userService.findAllMyRoomsById(userId));
    }

    @PostMapping("create-room")
    public ResponseEntity<?> createRoomChat(@RequestBody RoomChatDto roomChatDto){
        return ResponseEntity.ok(userService.createRoomChat(roomChatDto));
    }


}
