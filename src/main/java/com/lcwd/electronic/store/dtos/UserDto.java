package com.lcwd.electronic.store.dtos;                    


import org.springframework.stereotype.Component;
import com.lcwd.electronic.store.validation.ImageNameValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Component
public class UserDto {     
	
	private int userId;
	
	@Size(min=3, max=15, message = "invalid username !")
	private String name;
	
	//@Email(message = "invalid email !")
	@Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "invalid email !")
	@NotBlank(message = "email is required !")
	private String email;
	
	@NotBlank(message = "password is required !")
	private String password;
	
	@Size(min=4, max=6, message = "invalid gender !")
	private String gender;
	
	@NotBlank(message = "write something about yourself")
	private String about;
	
	@ImageNameValid  
	private String imageName;
	
	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", gender=" + gender + ", about=" + about + ", imageName=" + imageName + "]";
	}
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
