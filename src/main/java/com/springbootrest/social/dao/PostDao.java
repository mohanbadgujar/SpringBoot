package com.springbootrest.social.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

import com.springbootrest.social.entities.Post;

public interface PostDao extends JpaRepository<Post, Long> {
	
    Page<Post> findByUserId(Long userId, Pageable pageable);
    
    Page<Post> findByUserIdIn(Collection<Long> userId, Pageable pageable);

    Optional<Post> findByIdAndUserId(Long id, Long userId);
}
