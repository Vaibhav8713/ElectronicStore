package com.lcwd.electronic.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lcwd.electronic.store.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{

}
