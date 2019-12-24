package com.sypron.courses.dao;

import com.sypron.courses.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User , Long> {
     User findByEmail(String email);
     boolean existsByUserName (String username);
     boolean existsByEmail (String email);
}
