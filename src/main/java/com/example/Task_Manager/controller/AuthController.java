package com.example.Task_Manager.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Task_Manager.TokenUtil;
import com.example.Task_Manager.model.Task;
import com.example.Task_Manager.model.User;
import com.example.Task_Manager.repository.User1;
import com.example.Task_Manager.repository.task_repo;



@RestController
@RequestMapping("/auth") 
public class AuthController {
	@Autowired
	private User1 repo;
	
	@Autowired
	private TokenUtil tokenutil;
	
	@Autowired
	private task_repo taskrepo;
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		repo.save(user);
		return "Registered";
	}
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		User dbUser=repo.findByUsername(user.getUsername());
		if(dbUser!=null && dbUser.getPassword().equals(user.getPassword())) {
			return tokenutil.generateToken(user.getUsername());
		}
		return "Invalid";
	}
	@DeleteMapping("/{username}")
	public String deleteUser(@PathVariable String username) {
		User user=repo.findByUsername(username);
		if(user==null) {
			return "User not found";
		}
		taskrepo.deleteAll(taskrepo.findByUsername(username));
		repo.delete(user);
		return "User and tasks deleted";
	}
	
	
}
