package com.sparta.outsideworld.repository;

import com.sparta.outsideworld.entity.Post;
import com.sparta.outsideworld.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글을 생성된 시간순으로 내림차순해서 가져옴
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllByUserOrderByCreatedAtDesc(User user);
}
