package com.springbootrest.social.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootrest.social.dao.FollowerDao;
import com.springbootrest.social.dao.PostDao;
import com.springbootrest.social.entities.Follower;
import com.springbootrest.social.entities.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

@RestController
public class FollowerController {

	@Autowired
	private FollowerDao followerDao;

	@Autowired
	private PostDao postDao;

	@GetMapping("/users/{userId}/followers/posts")
	public Page<Post> getAllPostsByUserId(@PathVariable (value = "userId") Long userId,
			Pageable pageable) {

		List<Follower> followers = followerDao.findByUserId(userId);
		List<Long> userIds = new ArrayList<>();

		for(Follower follower: followers) {
			userIds.add(follower.getFollowerId());
		}

		pageable = PageRequest.of(0, 20, Sort.by("updatedAt").descending());
		return postDao.findByUserIdIn(userIds, pageable);
	}

	@PostMapping("/users/{userId}/followers/{followerId}")
	public String createFollower(@PathVariable (value = "userId") Long userId,
			@PathVariable (value = "followerId") Long followerId) {
		if(!userId.equals(followerId)) {
			Follower  follower = new Follower();
			follower.setUserId(userId);
			follower.setFollowerId(followerId);
			followerDao.save(follower);
			return "Follower added successfully";
		}else{
			return "User and follower must be different.";
		}
	}

}
