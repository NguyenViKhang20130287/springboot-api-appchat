package com.springboot.api_appchat.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roommembers")
public class RoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    @ManyToOne
    @Column(name = "chat_room_id")
    private int chatRoomId;

//    @ManyToOne
    @Column(name = "user_id")
    private int userId;

    @Column(name = "joined_at")
    private String joinedAt;
}
