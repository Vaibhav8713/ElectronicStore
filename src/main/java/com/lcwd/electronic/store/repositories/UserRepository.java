package com.lcwd.electronic.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lcwd.electronic.store.entities.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User,Integer>{
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndPassword(String email, String password);
	List<User> findByNameContaining(String keyword);
}
