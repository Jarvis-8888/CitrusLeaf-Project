package com.example.payload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
	
	//Dynamic Path
	//public static String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();
	//Static Path
	public static String UPLOAD_DIR= "C:\\Users\\Lenovo\\OneDrive\\Desktop\\Project\\CitrusLeaf\\src\\main\\resources\\static\\images";
	
	public FileUploadHelper() throws IOException {
	
	}
	
	public boolean uploadFile(MultipartFile multipartFile)
	{
		boolean value=false;
		
		try {
			
			Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			value=true;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}



	

	
}
