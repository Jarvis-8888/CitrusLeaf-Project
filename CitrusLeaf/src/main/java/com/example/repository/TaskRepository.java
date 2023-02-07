package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	@Query(name = "select * from tasks where user_id= ?1", nativeQuery = true)
	List<Task> findByUserId(Long userId);

	

}
