package com.wd.api.repository;

import com.wd.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
