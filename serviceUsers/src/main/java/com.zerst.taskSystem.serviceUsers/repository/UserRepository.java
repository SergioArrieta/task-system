package com.zerst.taskSystem.serviceUsers.repository;

import com.zerst.taskSystem.serviceUsers.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}