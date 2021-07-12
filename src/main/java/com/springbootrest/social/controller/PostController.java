package com.springbootrest.social.controller;

import org.springframework.web.bind.annotation.RestController;
import com.springbootrest.social.exception.ResourceNotFoundException;

import com.springbootrest.social.dao.PostDao;
import com.springbootrest.social.dao.UserDao;
import com.springbootrest.social.entities.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PostController {

	@Autowired
	private PostDao postDao;

	@Autowired
	private UserDao userDao;

	@GetMapping("/users/{userId}/posts")
	public Page<Post> getAllPostsByUserId(@PathVariable (value = "userId") Long userId,
			Pageable pageable) {
		return postDao.findByUserId(userId, pageable);
	}

	@PostMapping("/users/{userId}/posts")
	public Post createPost(@PathVariable (value = "userId") Long userId,
			@Valid @RequestBody Post post) {
		return userDao.findById(userId).map(user -> {
			post.setUser(user);
			return postDao.save(post);
		}).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
	}


	@PutMapping("/users/{userId}/posts/{postId}")
	public Post updatePost(@PathVariable (value = "userId") Long userId,
			@PathVariable (value = "postId") Long postId,
			@Valid @RequestBody Post postRequest) {
		if(!userDao.existsById(userId)) {
			throw new ResourceNotFoundException("UserId " + userId + " not found");
		}

		return postDao.findById(postId).map(post -> {
			post.setText(postRequest.getText());
			return postDao.save(post);
		}).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + "not found"));
	}

	@DeleteMapping("/users/{userId}/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable (value = "userId") Long userId,
			@PathVariable (value = "postId") Long postId) {
		return postDao.findByIdAndUserId(postId, userId).map(post -> {
			postDao.delete(post);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + postId + " and userId " + userId));
	}

}
