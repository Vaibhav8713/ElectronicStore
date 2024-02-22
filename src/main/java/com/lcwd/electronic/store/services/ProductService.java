package com.lcwd.electronic.store.services;

import java.util.List;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;

public interface ProductService {
	
	//CREATE
	ProductDto create(ProductDto productDto);
	
	//UPDATE
	ProductDto update(ProductDto ProductDtoWithNewDeatils, String productId);
	
	//DELETE
	void delete(String productId);
	
	//GET SINGLE PRODUCT
	ProductDto get(String productId);
	
	//GET ALL PRODUCTS
	PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//GET ALL LIVE PRODUCTS
	PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//SEARCH PRODUCT
	PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);
}






