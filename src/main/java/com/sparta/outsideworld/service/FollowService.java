package com.sparta.outsideworld.service;

import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.PostResponseDto;
import com.sparta.outsideworld.entity.Follow;
import com.sparta.outsideworld.entity.User;
import com.sparta.outsideworld.repository.FollowRepository;
import com.sparta.outsideworld.repository.PostRepository;
import com.sparta.outsideworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    // 팔로우 기능 API
    @Transactional
    public ApiResponseDto followUser(Long followingId, Long followerId) {
        User followingUser = userRepository.findById(followingId).orElseThrow(
                () -> new NullPointerException("팔로잉할 상대가 존재하지 않습니다.")
        );

        User followerUser = userRepository.findById(followerId).orElseThrow(
                () -> new NullPointerException("팔로우를 신청한 사용자가 존재하지 않습니다.")
        );

        if(followRepository.existsByFollowerAndFollowing(followerUser, followingUser)) {
            return new ApiResponseDto("이미 팔로우를 하셨습니다.", 400);
        }

        Follow follow = new Follow(followerUser, followingUser);
        followRepository.save(follow);
        return new ApiResponseDto("팔로우에 성공하였습니다.", 200);

    }

    // 언팔로우 기능 API
    @Transactional
    public ApiResponseDto unfollowUser(Long followingId, Long followerId) {
        User followingUser = userRepository.findById(followingId).orElseThrow(
                () -> new NullPointerException("언팔로잉할 상대가 존재하지 않습니다.")
        );

        User followerUser = userRepository.findById(followerId).orElseThrow(
                () -> new NullPointerException("언팔로우를 신청한 사용자가 존재하지 않습니다.")
        );

        if(!followRepository.existsByFollowerAndFollowing(followerUser, followingUser)) {
            return new ApiResponseDto("팔로우가 되어있지 않습니다.", 400);
        }

        followRepository.deleteByFollowerAndFollowing(followerUser, followingUser);
        return new ApiResponseDto("언팔로우에 성공하였습니다.", 200);
    }

    // 팔로우 한 유저의 전체게시글 조회 API
    @Transactional
    public List<PostResponseDto> getFollowPosts(Long followerId) {
        List<Follow> followList = followRepository.findByFollowerId(followerId);
        if(followList.isEmpty()){
            throw new IllegalArgumentException("팔로우 하는 사용자가 존재하지 않습니다.");
        }
        List<User> followingUser = followList.stream().map(Follow::getFollowing).collect(Collectors.toList());
        List<PostResponseDto> followUserPost = new ArrayList<>();

        for(User user : followingUser){
            List<PostResponseDto> posts = postRepository.findAllByUserOrderByCreatedAtDesc(user)
                    .stream().map(PostResponseDto::new).collect(Collectors.toList());
                    followUserPost.addAll(posts);
        }

        return followUserPost;
    }

    // 팔로우 한 특정 유저의 전체게시글 조회 API
//    public List<PostResponseDto> getFollowPost(Long following_Id, Long follower_Id) {
//
//    }
}
