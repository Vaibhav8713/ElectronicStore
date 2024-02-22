package com.lcwd.electronic.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
									// CREATE //
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
		ProductDto createProductDto = productService.create(productDto);
		return new ResponseEntity<>(createProductDto, HttpStatus.CREATED);
	}
	
	
									// UPDATE //
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDtoWithNewDetails)
	{
		ProductDto updateProductDto = productService.update(productDtoWithNewDetails, productId);
		return new ResponseEntity<>(updateProductDto, HttpStatus.OK);
	}
	
	
									// DELETE //
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId)
	{	
		productService.delete(productId);
		ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
		apiResponseMessage.setMessage("product with id :" + productId + " got successfully deleted");
		apiResponseMessage.setStatus(HttpStatus.OK);
		apiResponseMessage.setSuccess(true);
		return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
	}
	
	
								// GET SINGLE PRODUCT //
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
		ProductDto productDto = productService.get(productId);
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}
	
	
								// GET ALL PRODUCTS //
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAllProducts
	(
		@RequestParam(value="pageNumber",defaultValue="0",   required=false)    int pageNumber,
		@RequestParam(value="pageSize",  defaultValue="10",  required=false)    int pageSize,
		@RequestParam(value="sortBy",    defaultValue="title", required=false)  String sortBy,
		@RequestParam(value="sortDir",   defaultValue="asc",required=false)    String sortDir	
	){
		PageableResponse<ProductDto> allProductsPageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(allProductsPageableResponse, HttpStatus.OK);
	}
	
	
							// GET ALL LIVE PRODUCTS //
	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts
	(
		@RequestParam(value="pageNumber",defaultValue="0",   required=false)    int pageNumber,
		@RequestParam(value="pageSize",  defaultValue="10",  required=false)    int pageSize,
		@RequestParam(value="sortBy",    defaultValue="title", required=false)  String sortBy,
		@RequestParam(value="sortDir",   defaultValue="asc",required=false)    String sortDir	
	){
		PageableResponse<ProductDto> allLiveProductsPageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(allLiveProductsPageableResponse, HttpStatus.OK);
	}
	
	
							      // SEARCH //
	@GetMapping("/search/{subTitle}")
	public ResponseEntity<PageableResponse<ProductDto>> searchProducts
	(
		@PathVariable String subTitle,	
		@RequestParam(value="pageNumber",defaultValue="0",   required=false)    int pageNumber,
		@RequestParam(value="pageSize",  defaultValue="10",  required=false)    int pageSize,
		@RequestParam(value="sortBy",    defaultValue="title", required=false)  String sortBy,
		@RequestParam(value="sortDir",   defaultValue="desc",required=false)    String sortDir	
	){
		PageableResponse<ProductDto> allProductsPageableResponse = productService.searchByTitle(subTitle,pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(allProductsPageableResponse, HttpStatus.OK);
	}
}








