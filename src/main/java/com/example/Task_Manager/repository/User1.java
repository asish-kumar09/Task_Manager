package com.example.Task_Manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Task_Manager.model.User;

public interface User1 extends JpaRepository<User,Long>{
	User findByUsername(String username);
}
