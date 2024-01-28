package com.springboot.api_appchat.Service;

import com.springboot.api_appchat.Dto.RoomChatDto;
import com.springboot.api_appchat.Entity.ChatRoom;
import com.springboot.api_appchat.Entity.RoomMember;
import com.springboot.api_appchat.Entity.User;
import com.springboot.api_appchat.Repository.ChatRoomRepository;
import com.springboot.api_appchat.Repository.RoomMemberRepository;
import com.springboot.api_appchat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private UserRepository userRepository;
    private ChatRoomRepository chatRoomRepository;
    private RoomMemberRepository roomMemberRepository;

    @Autowired
    public ChatRoomServiceImpl(UserRepository userRepository, ChatRoomRepository chatRoomRepository,
                               RoomMemberRepository roomMemberRepository) {
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.roomMemberRepository = roomMemberRepository;
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
        RoomMember roomMember = new RoomMember();
        roomMember.setChatRoom(chatRoom);
        roomMember.setMember(user);
        roomMember.setJoinedAt(String.valueOf(LocalDateTime.now()));
        roomMemberRepository.save(roomMember);
        return new ResponseEntity<>(chatRoom, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteRoomChat(long userId, long chatRoomId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return new ResponseEntity<>("User not found!!!", HttpStatus.NOT_FOUND);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);
        if (chatRoom == null) return new ResponseEntity<>("Room not found!!!", HttpStatus.NOT_FOUND);
        chatRoomRepository.delete(chatRoom);
        return new ResponseEntity<>("Delete Room has id:" + chatRoomId + " successful", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findRoomByName(String roomName) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomName(roomName);
        if (chatRoom == null) return new ResponseEntity<>("No result!!!", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(chatRoom, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addUserToRoom(long roomId, long userId, long hostId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return new ResponseEntity<>("User not found!!!", HttpStatus.NOT_FOUND);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        if (chatRoom == null) return new ResponseEntity<>("Room not found!!!", HttpStatus.NOT_FOUND);

        // Kiem tra phai host hay khong ?
        if (hostId != chatRoom.getHost().getId())
            return new ResponseEntity<>("You do not have permission to perform this action", HttpStatus.BAD_REQUEST);

        List<RoomMember> memberList = chatRoom.getRoomMembers();
        for (RoomMember member : memberList) {
            if (userId == member.getMember().getId()) {
                return new ResponseEntity<>("The user has joined the room", HttpStatus.BAD_REQUEST);
            }
        }

        RoomMember roomMember = new RoomMember();
        roomMember.setChatRoom(chatRoom);
        roomMember.setMember(user);
        roomMember.setJoinedAt(String.valueOf(LocalDateTime.now()));

        roomMemberRepository.save(roomMember);
        return new ResponseEntity<>(roomMember, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeUserFromRoom(long roomId, long userId, long hostId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return new ResponseEntity<>("User not found!!!", HttpStatus.NOT_FOUND);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        if (chatRoom == null) return new ResponseEntity<>("Room not found!!!", HttpStatus.NOT_FOUND);

        // Kiem tra phai host hay khong ?
        if (hostId != chatRoom.getHost().getId())
            return new ResponseEntity<>("You do not have permission to perform this action", HttpStatus.BAD_REQUEST);

        RoomMember roomMember = roomMemberRepository.findByChatRoomIdAndMemberId(roomId, userId);
        if (roomMember == null)
            return new ResponseEntity<>("The user has not joined the chat room !!!", HttpStatus.NOT_FOUND);
        roomMemberRepository.delete(roomMember);
        return new ResponseEntity<>("Remove user successful", HttpStatus.OK);
    }

}
