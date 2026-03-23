package com.example.Task_Manager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Task_Manager.model.Task;

public interface task_repo extends JpaRepository<Task,Long>{
	List<Task> findByUsername(String username);
}
