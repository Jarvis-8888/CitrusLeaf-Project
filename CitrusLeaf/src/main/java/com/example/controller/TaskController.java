package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.FileResponse;
import com.example.model.Task;
import com.example.payload.ApiResponse;
import com.example.payload.FileUploadHelper;
import com.example.service.FileService;
import com.example.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/task")
public class TaskController {
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// Upload file
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(@RequestParam("file") MultipartFile multipartFile) {
		String uploadFile = null;
		try {
			uploadFile = this.fileService.uploadFile(path, multipartFile);

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<FileResponse>(new FileResponse(null, "File not uploaded due to server error !!!"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<FileResponse>(new FileResponse(uploadFile, "File is successfully uploaded !!!"),
				HttpStatus.OK);
	}

	// Serve file
	@GetMapping(value = "/file/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("fileName") String fileName, HttpServletResponse response)
			throws IOException {
		InputStream resource = this.fileService.getResource(path, fileName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

	// Upload file and data at a time
	@PostMapping("/user/{userId}")
	public ResponseEntity<Task> saveTask(
			@RequestParam("taskData") String taskData,
			@PathVariable Long userId,
			@RequestParam("file") MultipartFile multipartFile) throws IOException
	{
		
		Task task = this.objectMapper.readValue(taskData, Task.class);
		String uploadFile = this.fileService.uploadFile(path, multipartFile);
		task.setFilePath(uploadFile);
		Task savedTask=this.taskService.saveTask(task,userId);
		return new ResponseEntity<Task>(savedTask,HttpStatus.CREATED);
	}
	
	
	// Get List of task of particular user
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Task>> listOfTask(@PathVariable Long userId)
	{
		List<Task> list=this.taskService.listOfTask(userId);
		return new ResponseEntity<List<Task>>(list,HttpStatus.OK);
	}
	
	
	//Update task
	
	@PutMapping("/taskId/{taskId}")
	public ResponseEntity<Task> updateTask(@RequestBody Task task,@PathVariable Long taskId)
	{
		Task updatedTask=this.taskService.updateTask(task,taskId);
		return new ResponseEntity<Task>(updatedTask,HttpStatus.OK);
	}
	
	//Delete task
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<ApiResponse> deleteTask(@PathVariable("taskId") Long taskId)
	{
		this.taskService.deleteTask(taskId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Task deleted successfully !!!", true),HttpStatus.OK);
	}
	

}
