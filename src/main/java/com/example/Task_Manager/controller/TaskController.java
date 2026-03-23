package com.example.Task_Manager.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/tasks")
public class TaskController {
	@Autowired
	private TokenUtil tokenutil;
	
	@Autowired
	private task_repo repo;
	@Autowired
	private User1 userRepo;
	
	@GetMapping("/admin")
	public Object getAllTasks(@RequestHeader("Authorization")String token) {
		String username=tokenutil.validateToken(token);
		if(username==null) {
			return "Unauthorized";
		}
		User user=userRepo.findByUsername(username);
		if(!user.getRole().equals("ADMIN")) {
			return "Access Denied";
		}
		return repo.findAll();
	}
	
	@PostMapping
	public Object createNode(@RequestBody Task task,@RequestHeader("Authorization") String token) {
		String username=tokenutil.validateToken(token);
		System.out.println("Received token: " + token);
	    System.out.println("Validated username: " + username);

		if(username==null) {
			return "Unauthorized";
		}
		task.setUsername(username);
		
		System.out.println("Received token: " + token);
		return repo.save(task);
	
		
	}
	
	@GetMapping
	public Object getNotes(@RequestHeader("Authorization")String token) {
		String username=tokenutil.validateToken(token);
		if(username==null) {
			return "Unauthorized";
		}
		return repo.findByUsername(username);
	}
	
	@PutMapping("/{id}")
	public Object updateNote(
	        @PathVariable Long id,
	        @RequestBody Task updateTask,
	        @RequestHeader("Authorization") String token) {
	    
	    String username = tokenutil.validateToken(token); 
	    
	    if (username == null) {
	        return "Unauthorized";
	    }
	    
	    Optional<Task> optionalNote = repo.findById(id);
	    if (optionalNote.isEmpty()) {
	        return "Note not found";
	    }
	    
	    Task existingTask = optionalNote.get();
	    if (!existingTask.getUsername().equals(username)) {
	        return "You cannot update this note";
	    }
	    
	    existingTask.setTitle(updateTask.getTitle());
	    existingTask.setDescription(updateTask.getDescription()); 
	    
	    return repo.save(existingTask);
	}	
	
}