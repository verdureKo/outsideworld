package com.sparta.outsideworld.service;


import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.PostRequestDto;
import com.sparta.outsideworld.dto.PostResponseDto;
import com.sparta.outsideworld.entity.Post;
import com.sparta.outsideworld.entity.User;
import com.sparta.outsideworld.entity.UserRoleEnum;
import com.sparta.outsideworld.jwt.JwtUtil;
import com.sparta.outsideworld.repository.PostRepository;
import com.sparta.outsideworld.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    // private final JwtUtil jwtUtil;

    // 게시글 전체 조회 API
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreateAtDesc();
        List<PostResponseDto> postResponseDto = new ArrayList<>();

        for(Post post : posts){
            postResponseDto.add(new PostResponseDto(post));
        }

        return postResponseDto;
    }

    // 게시글 선택 조회 API
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택하신 게시물은 존재하지 않습니다.")
        );

        return new PostResponseDto(post);

    }

    // 게시글 등록 API
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        if(user == null){
            throw new IllegalArgumentException("허가되지 않은 사용자입니다.");
        }

        Post post = new Post(postRequestDto, user);
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    // 게시글 수정 API
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택하신 게시물은 존재하지 않습니다.")
        );

        if(post.getUser().getId().equals(user.getId()) || user.getRole().equals(UserRoleEnum.ADMIN)){
            post.update(postRequestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }

        return new PostResponseDto(post);
    }


    // 게시글 삭제 API
    @Transactional
    public ApiResponseDto deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("선택하신 게시물은 존재하지 않습니다.")
        );
        if(post.getUser().getId().equals(user.getId()) || user.getRole().equals(UserRoleEnum.ADMIN)){
            postRepository.delete(post);
        } else {
            return new ApiResponseDto("작성자만 삭제가 가능합니다.",400);
        }
        return new ApiResponseDto("삭제가 완료되었습니다.",200);
    }
}
