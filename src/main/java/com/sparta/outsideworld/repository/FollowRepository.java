package com.sparta.outsideworld.repository;

import com.sparta.outsideworld.entity.Follow;
import com.sparta.outsideworld.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerId(Long followerId);
    boolean existsByFollowerAndFollowing(User follower_Id, User following_Id);
    void deleteByFollowerAndFollowing(User follower_Id, User following_Id);

}
