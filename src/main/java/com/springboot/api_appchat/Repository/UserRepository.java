package com.springboot.api_appchat.Repository;

import com.springboot.api_appchat.Entity.ChatRoom;
import com.springboot.api_appchat.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByDisplayNameIgnoreCase (String displayName);
}
