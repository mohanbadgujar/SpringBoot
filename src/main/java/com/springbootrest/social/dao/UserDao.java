package com.springbootrest.social.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootrest.social.entities.User;

public interface UserDao extends JpaRepository<User, Long> {
	
}
