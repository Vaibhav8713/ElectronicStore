package com.lcwd.electronic.store.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.accept.MediaTypeFileExtensionResolver;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.ImageResponse;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	  
								//create
	@PostMapping	 
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto userDto1 = userService.createUser(userDto); 
		return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
	}
	 
								//update
	@PutMapping("/{userId}")   
	public ResponseEntity<UserDto> updateUser(@PathVariable("userId") int id, @Valid @RequestBody UserDto userDto){
		UserDto updatedUserDto = userService.updateUser(userDto, id); 
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
	} 
	 
								//delete 
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") int id){
		userService.deleteUser(id);
		ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
		apiResponseMessage.setMessage("user with given id : " + id + " is successfully deleted !");
		apiResponseMessage.setSuccess(true);
		apiResponseMessage.setStatus(HttpStatus.OK);
		return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
	}
	
								//get all users
	@GetMapping  
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers
			( 
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber, 
			@RequestParam(value="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = "asc", required = false) String sortDir
			)
	{
		return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK); 
	}
	
								//get single user by id
	@GetMapping("/{userId}") 
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") int id){
		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}
	
								//get single user by email 
	@GetMapping("/email/{userEmail}") 
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable("userEmail") String email){
		return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
	}
	
								//search user by keyword
	@GetMapping("/search/{keyword}")  
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
		return new ResponseEntity<>(userService.searchUser(keyword), HttpStatus.OK);
	}
	
								//upload user image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable int userId){
		String imageName = fileService.uploadFile(image, imageUploadPath);
		
		UserDto user = userService.getUserById(userId);
		user.setImageName(imageName);
		userService.updateUser(user, userId);
		
		ImageResponse imageResponse = new ImageResponse();
		imageResponse.setImageName(imageName);
		imageResponse.setMessage("=> image uploaded successfully!");
		imageResponse.setStatus(HttpStatus.CREATED); 
		imageResponse.setSuccess(true);
		return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);
	}
	
							//serve user image
	@GetMapping("image/{userId}")
	public void serveUserImage(@PathVariable int userId, HttpServletResponse response) throws IOException {
		UserDto user = userService.getUserById(userId);
		System.out.println("=> user image name = " + user.getImageName());
		InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
} 

















