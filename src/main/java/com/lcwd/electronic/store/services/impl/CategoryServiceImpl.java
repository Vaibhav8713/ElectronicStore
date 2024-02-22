package com.lcwd.electronic.store.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.CategoryRepository;
import com.lcwd.electronic.store.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Value("${category.cover.image.path}")
	private String imagePath;

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		
		//creating category id randomly
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);
		Category category = DtoToEntity(categoryDto);
		Category savedCategory = categoryRepository.save(category);
		return EntityToDto(savedCategory); 
	}

	private CategoryDto EntityToDto(Category savedCategory) {
		CategoryDto c1 = new CategoryDto();
		c1.setCategoryId(savedCategory.getCategoryId());
		c1.setCoverImage(savedCategory.getCoverImage());
		c1.setDescription(savedCategory.getDescription());
		c1.setTitle(savedCategory.getTitle());
		return c1;
	}

	private Category DtoToEntity(CategoryDto categoryDto) {
		Category c1 = new Category();
		c1.setCategoryId(categoryDto.getCategoryId());
		c1.setCoverImage(categoryDto.getCoverImage());
		c1.setDescription(categoryDto.getDescription());
		c1.setTitle(categoryDto.getTitle());
		return c1;
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {
		Category c = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category with given id :"+categoryId+" not found"));
		//c.setCategoryId(categoryDto.getCategoryId());
		c.setCoverImage(categoryDto.getCoverImage());
		c.setDescription(categoryDto.getDescription());
		c.setTitle(categoryDto.getTitle());
		Category savedCategory = categoryRepository.save(c);
		return EntityToDto(savedCategory);
	}

	@Override
	public void delete(String categoryId) {
		Category c = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category with given id :"+categoryId+" not found"));
		
				//delete user profile image
				String fullpath = imagePath + c.getCoverImage();
				try {
					java.nio.file.Path path = Paths.get(fullpath);
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		categoryRepository.delete(c);
	}

	@Override
	public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		org.springframework.data.domain.Page<Category> page = categoryRepository.findAll(pageable);
		List<Category> allCategories = page.getContent();
		
		List<CategoryDto> allCategoryDtos = new ArrayList<>();
		for(Category c : allCategories) {
			allCategoryDtos.add(EntityToDto(c));
		}
		
		PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
		pageableResponse.setContent(allCategoryDtos);
		pageableResponse.setPageNumber(page.getNumber()+1); 
		pageableResponse.setPageSize(page.getSize());
		pageableResponse.setTotalElement(page.getTotalElements());
		pageableResponse.setTotalPages(page.getTotalPages());
		pageableResponse.setLastPage(page.isLast()); 
		return pageableResponse;
	}

	@Override
	public CategoryDto get(String categoryId) {
		Category c = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category with given id :"+categoryId+" not found"));
		return EntityToDto(c);
	}

}
