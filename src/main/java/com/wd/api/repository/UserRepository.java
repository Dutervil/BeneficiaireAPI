package com.wd.api.repository;

import com.wd.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
    @Query("SELECT COUNT(u) FROM User u")
    Long getTotalCount();
}
