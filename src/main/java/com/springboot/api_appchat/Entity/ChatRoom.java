package com.springboot.api_appchat.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chatrooms")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String roomName;

    @Column(name = "password_room")
    private String passwordRoom;

    @Column(name = "created_at")
    private String createdAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;

//    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom")
    private List<RoomMember> roomMembers;

}
