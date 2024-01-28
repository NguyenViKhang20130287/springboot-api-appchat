package com.springboot.api_appchat.Repository;

import com.springboot.api_appchat.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
