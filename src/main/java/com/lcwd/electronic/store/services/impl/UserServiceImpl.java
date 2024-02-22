package com.lcwd.electronic.store.services.impl;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;

import jakarta.persistence.criteria.Path;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${user.profile.image.path}")
	private String imagePath;

	@Override
	public UserDto createUser(UserDto userDto) {
		User u1 = dtoToEntity(userDto);
		User savedUser = userRepository.save(u1);
		UserDto newDto = EntityToDto(savedUser);
		return newDto; 
	} 
	
	private UserDto EntityToDto(User savedUser) { 
		UserDto userDto = new UserDto();
		userDto.setUserId(savedUser.getUserId());
		userDto.setName(savedUser.getName());
		userDto.setAbout(savedUser.getAbout());
		userDto.setEmail(savedUser.getEmail()); 
		userDto.setGender(savedUser.getGender());
		userDto.setImageName(savedUser.getImageName());
		userDto.setPassword(savedUser.getPassword());
		return userDto;
	}

	private User dtoToEntity(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout()); 
		user.setEmail(userDto.getEmail()); 
		user.setGender(userDto.getGender());
		user.setImageName(userDto.getImageName());
		user.setPassword(userDto.getPassword());
		return user;
	}

	@Override  
	public UserDto updateUser(UserDto userDto, int userId) {
		User u1 = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with given id : "+ userId));
		u1.setName(userDto.getName());
		u1.setAbout(userDto.getAbout());
		u1.setGender(userDto.getGender());
		u1.setImageName(userDto.getImageName());
		u1.setImageName(userDto.getImageName());
		
		//save updated data
		User savedUser = userRepository.save(u1); 
		UserDto updateDto = EntityToDto(savedUser);
		return updateDto; 
	}

	@Override
	public void deleteUser(int userId) {
		User u1 = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with given id : "+ userId));
		
		//delete user profile image
		String fullpath = imagePath + u1.getImageName();
		
		try {
			java.nio.file.Path path = Paths.get(fullpath);
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		userRepository.delete(u1);
	} 

	@Override
	public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort =  (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		// pageNumber default starts from 0
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<User> page = userRepository.findAll(pageable);
		List<User> allUsers = page.getContent();
		List<UserDto> allUserDtos = new ArrayList<>();
		for(User user : allUsers) { 
			allUserDtos.add(EntityToDto(user));
		}
		
		PageableResponse<UserDto> response = new PageableResponse<>();
		response.setContent(allUserDtos);
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElement(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		response.setLastPage(page.isLast()); 
		return response;
	} 

	@Override
	public UserDto getUserById(int userId) {
		User u1 = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with given id : "+ userId +" not found."));
		return EntityToDto(u1);
	} 

	@Override
	public UserDto getUserByEmail(String email) {
		User u1 = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user with given email : " + email + " not found."));
		return EntityToDto(u1);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users = userRepository.findByNameContaining(keyword);
		List<UserDto> UserDtos = new ArrayList<>();
		for(User user : users) {
			UserDtos.add(EntityToDto(user));
		}
		return UserDtos;
	}

}
