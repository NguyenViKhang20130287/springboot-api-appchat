package com.springboot.api_appchat.Controller;

import com.springboot.api_appchat.Dto.RoomChatDto;
import com.springboot.api_appchat.Service.ChatRoomService;
import com.springboot.api_appchat.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {
    private UserService userService;
    private ChatRoomService chatRoomService;

    @Autowired
    public UserController(UserService userService, ChatRoomService chatRoomService) {
        this.userService = userService;
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("my-rooms")
    public ResponseEntity<?> findHostedChatRoomsById(@RequestParam long userId) {
        return ResponseEntity.ok(chatRoomService.findAllMyRoomsById(userId));
    }

    @PostMapping("create-room")
    public ResponseEntity<?> createRoomChat(@RequestBody RoomChatDto roomChatDto) {
        return ResponseEntity.ok(chatRoomService.createRoomChat(roomChatDto));
    }

    @DeleteMapping("delete-room")
    public ResponseEntity<?> deleteRoomChat(@RequestParam long userId, @RequestParam long chatRoomId) {
        return ResponseEntity.ok(chatRoomService.deleteRoomChat(userId, chatRoomId));
    }

    @GetMapping("find-rooms")
    public ResponseEntity<?> findAllRoomsByRoomName(String roomName){
        return ResponseEntity.ok(chatRoomService.findRoomByName(roomName));
    }

    @PostMapping("add-user-to-room")
    public ResponseEntity<?> addUserToRoom(@RequestParam long roomId, @RequestParam long userId, @RequestParam long hostId){
        return ResponseEntity.ok(chatRoomService.addUserToRoom(roomId, userId, hostId));
    }

}
