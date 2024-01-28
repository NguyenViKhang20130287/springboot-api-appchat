package com.springboot.api_appchat.Repository;

import com.springboot.api_appchat.Entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findAllByChatRoomId(long chatRoomId);
}
