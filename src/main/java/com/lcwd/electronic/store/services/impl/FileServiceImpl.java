package com.lcwd.electronic.store.services.impl;

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

import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadFile(MultipartFile file, String path) {
		System.out.println("=>path = " + path);
		String originalFileName = file.getOriginalFilename();
		System.out.println("==> original filename = " + originalFileName);
		String filename = UUID.randomUUID().toString();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String fileNameWithExtension = filename + extension;
		String fullPathWithFileName = path + fileNameWithExtension;
		System.out.println("=>full path with file name = " + fullPathWithFileName);
		if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg"))
		{
			//save file
			System.out.println("=> extension = "+extension);
			File folder = new File(path);
			if(!folder.exists()) {
				//create folder
				folder.mkdirs();
			}
			
			//upload
			try {
				Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return fileNameWithExtension;
		}
		else {
			throw new BadApiRequest("file with extension "+extension+" is not allowed !");
		}
	} 

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		String fullPath = path+name;
		System.out.println("==> fullpath = "+fullPath);
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream; 
	}

}
