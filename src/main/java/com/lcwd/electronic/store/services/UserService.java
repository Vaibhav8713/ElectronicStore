package com.lcwd.electronic.store.services;
import java.util.List;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;

public interface UserService {
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, int userId);
	void deleteUser(int userId); 
	PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
	UserDto getUserById(int userId);
	UserDto getUserByEmail(String email);
	List<UserDto> searchUser(String keyword);
}
