package com.sparta.outsideworld.repository;

import com.sparta.outsideworld.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByCreatedTimeDesc();
}
