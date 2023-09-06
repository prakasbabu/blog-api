package com.prakash.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.sym.Name;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//get fileName
		String fileName = file.getOriginalFilename();
		
		String randomId =UUID.randomUUID().toString();
		
		String fileName1 =	randomId.concat(fileName.substring(fileName.lastIndexOf(".") ));	
		
		//complete path to file
		String filePath = path +File.separator +fileName1 ;
		
			
		
		//create folder if not existed
				File f = new File (path);
				
				if ( !f.exists()) {
					f.mkdir();
				}
				
		//copy file, target and source
				
			Files.copy(file.getInputStream(),Paths.get(filePath));	
				
				
		//return fileName		
		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path+ File.separator+fileName;
		
		InputStream inputStream = new FileInputStream(fullPath);
						
		return inputStream;
	}

}
