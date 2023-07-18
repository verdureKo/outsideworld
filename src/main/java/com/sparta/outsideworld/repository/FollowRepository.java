package com.sparta.outsideworld.repository;

import com.sparta.outsideworld.entity.Follow;
import com.sparta.outsideworld.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(Long follower_Id);
    List<Follow> findByFollowing(Long following_Id);
    boolean existsByFollowerAndFollowing(User follower_Id, User following_Id);
    void deleteByFollowerAndFollowing(User follower_Id, User following_Id);

}
