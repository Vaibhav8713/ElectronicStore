package com.lcwd.electronic.store.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.ImageResponse;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.services.CategoryService;
import com.lcwd.electronic.store.services.FileService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired 
	private FileService fileService;
	
	@Value("${category.cover.image.path}")
	private String imageUploadPath;
	
									// create category //
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		//call service to create the object
		CategoryDto categoryDto1 = categoryService.create(categoryDto);
		return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED); 
	}
	
								    // update category //
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDtoWithNewDetails, @PathVariable String categoryId){
		CategoryDto updateCategoryDto = categoryService.update(categoryDtoWithNewDetails, categoryId);
		return new ResponseEntity<>(updateCategoryDto, HttpStatus.OK);
	}
	
							        // delete category //
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
		categoryService.delete(categoryId);
		ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
		apiResponseMessage.setMessage("category with id : "+categoryId+" deleted successfully!");
		apiResponseMessage.setStatus(HttpStatus.OK);
		apiResponseMessage.setSuccess(true);
		return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
	}
	
							       // get all //
	@GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAll
	( 
		@RequestParam(value="pageNumber",defaultValue="0",   required=false)  int pageNumber,
		@RequestParam(value="pageSize",  defaultValue="10",  required=false)  int pageSize,
		@RequestParam(value="sortBy",    defaultValue="title", required=false)  String sortBy,
		@RequestParam(value="sortDir",   defaultValue="desc",required=false)  String sortDir
	){ 
		PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(pageableResponse, HttpStatus.OK); 
	}
	
						         // get single category //
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId){
		CategoryDto categoryDto = categoryService.get(categoryId);
		return new ResponseEntity<>(categoryDto, HttpStatus.OK); 
	}
	
	
								// upload cover image //
	@PostMapping("/image/{categoryId}")
	public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam("coverImage") MultipartFile image, @PathVariable String categoryId)
	{
		String imageName = fileService.uploadFile(image, categoryId);
		
		CategoryDto categoryDto = categoryService.get(categoryId);
		categoryDto.setCoverImage(imageName);
		categoryService.update(categoryDto, categoryId);
		
		ImageResponse imageResponse = new ImageResponse();
		imageResponse.setImageName(imageName);
		imageResponse.setMessage("cover image uploaded successfully!");
		imageResponse.setStatus(HttpStatus.CREATED);
		imageResponse.setSuccess(true);
		return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
	}
	
								// serve cover image //
	@GetMapping("/image/{categoryId}")
	public void serverCoverImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
		CategoryDto categoryDto = categoryService.get(categoryId);
		System.out.println("=> cover image name = "+ categoryDto.getCoverImage());
		InputStream resource = fileService.getResource(imageUploadPath, categoryDto.getCoverImage());
		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
	}
}










