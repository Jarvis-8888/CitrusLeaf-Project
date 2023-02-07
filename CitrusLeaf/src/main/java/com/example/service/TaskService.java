package com.example.service;

import java.util.List;

import com.example.model.Task;

public interface TaskService {

	Task saveTask(Task task, Long userId);

	List<Task> listOfTask(Long userId);

	void deleteTask(Long taskId);

	Task updateTask(Task task, Long taskId);

}
