package com.lcwd.electronic.store.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lcwd.electronic.store.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	//search
	Page<Product> findByTitleContaining(String SubTitle, Pageable pageable);
	Page<Product> findByLiveTrue(Pageable pageable);
}
