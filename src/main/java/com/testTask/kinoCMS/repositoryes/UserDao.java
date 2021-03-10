package com.testTask.kinoCMS.repositoryes;

import com.testTask.kinoCMS.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
