package com.springbootrest.social.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springbootrest.social.entities.Follower;

public interface FollowerDao extends JpaRepository<Follower, Long> {
	List<Follower> findByUserId(Long userId);
}

