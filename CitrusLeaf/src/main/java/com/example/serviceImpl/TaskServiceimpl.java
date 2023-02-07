package com.example.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Task;
import com.example.model.User;
import com.example.repository.TaskRepository;
import com.example.repository.UserRepository;
import com.example.service.TaskService;

@Service
public class TaskServiceimpl implements TaskService{

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Task saveTask(Task task, Long userId) {
		 User user = this.userRepository.findById(userId).get();
		 task.setUser(user);
		return this.taskRepository.save(task);
	}

	@Override
	public List<Task> listOfTask(Long userId) {
		List<Task> list=this.taskRepository.findByUserId(userId);
		return list;
	}

	@Override
	public void deleteTask(Long taskId) {
		Task task = this.taskRepository.findById(taskId).orElseThrow(()->new ResourceNotFoundException("Task", "taskId", taskId));
		this.taskRepository.delete(task);
	}

	@Override
	public Task updateTask(Task task, Long taskId) {
		Task oldTask = this.taskRepository.findById(taskId).get();
		oldTask.setTitle(task.getTitle());
		oldTask.setDescription(task.getDescription());
		oldTask.setDueDate(task.getDueDate());
		oldTask.setDone(task.isDone());
		return this.taskRepository.save(oldTask);
	}

}
