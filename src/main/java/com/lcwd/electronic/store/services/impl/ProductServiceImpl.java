package com.lcwd.electronic.store.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public ProductDto create(ProductDto productDto) {
		Product product = DtoToEntity(productDto);
		
		// GENERATE RANDOM PRODUCT ID
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);
		// SET ADDED DATE
		product.setAddedDate(new Date(0));
		
		// NOW SAVE THE PRODUCT INTO THE DATABASE
		Product savedProduct = productRepository.save(product);
		System.out.println("=> saved product = "+savedProduct);
		return EntityToDto(savedProduct);
	}

	private Product DtoToEntity(ProductDto productDto) {
		Product p = new Product();
		p.setAddedDate(productDto.getAddedDate());
		p.setDiscountedPrice(productDto.getDiscountedPrice());
		p.setDiscription(productDto.getDiscription());
		p.setLive(productDto.isLive());
		p.setPrice(productDto.getPrice());
		p.setQuantity(productDto.getQuantity());
		p.setStock(productDto.isStock());
		p.setTitle(productDto.getTitle());
		//p.setProductId(productDto.getProductId());
		return p;
	}

	private ProductDto EntityToDto(Product savedProduct) {
		ProductDto productDto = new ProductDto();
		productDto.setAddedDate(savedProduct.getAddedDate());
		productDto.setDiscountedPrice(savedProduct.getDiscountedPrice());
		productDto.setDiscription(savedProduct.getDiscription());
		productDto.setLive(savedProduct.isLive());
		productDto.setPrice(savedProduct.getPrice());
		productDto.setQuantity(savedProduct.getQuantity());
		productDto.setStock(savedProduct.isStock());
		productDto.setTitle(savedProduct.getTitle());
		productDto.setProductId(savedProduct.getProductId());
		return productDto;
	}

	@Override
	public ProductDto update(ProductDto ProductDtoWithNewDeatils, String productId) {
		
		//CHECK IF THE PRODUCT WITH GIVEN ID EXIST OF NOT
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with id : "+productId+" not exist"));
		
		//UPDATE THE PRODUCT WITH NEW DETAILS
		product.setAddedDate(ProductDtoWithNewDeatils.getAddedDate());
		product.setDiscountedPrice(ProductDtoWithNewDeatils.getDiscountedPrice());
		product.setDiscription(ProductDtoWithNewDeatils.getDiscription());
		product.setLive(ProductDtoWithNewDeatils.isLive());
		product.setPrice(ProductDtoWithNewDeatils.getPrice());
		product.setQuantity(ProductDtoWithNewDeatils.getQuantity());
		product.setStock(ProductDtoWithNewDeatils.isStock());
		product.setTitle(ProductDtoWithNewDeatils.getTitle());
		
		//NOW SAVE THE UPDATED PRODUCT DETAILS
		Product updatedProduct = productRepository.save(product);
		
		//NOW CONVERT UPDATED PRODUCT INTO DTO THEN RETURN THE DTO PRODUCT
		return EntityToDto(updatedProduct);
	}

	@Override
	public void delete(String productId) {
		//CHECK IF THE PRODUCT WITH GIVEN ID EXIST OF NOT
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with id : "+productId+" not exist"));
		
		//NOW DELETE THE PRODUCT FROM THE DATABASE USING REPOSITORY CLASS
		productRepository.delete(product);
	}

	@Override
	public ProductDto get(String productId) {
		//CHECK IF THE PRODUCT WITH GIVEN ID EXIST OF NOT
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with id : "+productId+" not exist"));
		return EntityToDto(product);		
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findAll(pageable);
		
		List<Product> allProducts= page.getContent();
		
		List<ProductDto> allProductsDto = new ArrayList<>();
		for(Product p : allProducts) {
			allProductsDto.add(EntityToDto(p));
		}
		
		PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
		pageableResponse.setContent(allProductsDto);
		pageableResponse.setPageNumber(page.getNumber()+1); 
		pageableResponse.setPageSize(page.getSize());
		pageableResponse.setTotalElement(page.getTotalElements());
		pageableResponse.setTotalPages(page.getTotalPages());
		pageableResponse.setLastPage(page.isLast()); 
		return pageableResponse;
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Product> page = productRepository.findByLiveTrue(pageable);
		
		List<Product> allProducts= page.getContent();
		
		List<ProductDto> allProductsDto = new ArrayList<>();
		for(Product p : allProducts) {
			allProductsDto.add(EntityToDto(p));
		}
		
		PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
		pageableResponse.setContent(allProductsDto);
		pageableResponse.setPageNumber(page.getNumber()+1); 
		pageableResponse.setPageSize(page.getSize());
		pageableResponse.setTotalElement(page.getTotalElements());
		pageableResponse.setTotalPages(page.getTotalPages());
		pageableResponse.setLastPage(page.isLast()); 
		return pageableResponse;
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
		 
		List<Product> allProducts= page.getContent();
		
		List<ProductDto> allProductsDto = new ArrayList<>();
		for(Product p : allProducts) {
			allProductsDto.add(EntityToDto(p));
		}
		
		PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
		pageableResponse.setContent(allProductsDto);
		pageableResponse.setPageNumber(page.getNumber()+1); 
		pageableResponse.setPageSize(page.getSize());
		pageableResponse.setTotalElement(page.getTotalElements());
		pageableResponse.setTotalPages(page.getTotalPages());
		pageableResponse.setLastPage(page.isLast()); 
		return pageableResponse;
	}

}
