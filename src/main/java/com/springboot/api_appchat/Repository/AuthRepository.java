package com.springboot.api_appchat.Repository;

import com.springboot.api_appchat.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByDisplayName(String displayName);
}
