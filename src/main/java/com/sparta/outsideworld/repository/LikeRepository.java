package com.sparta.outsideworld.repository;

import com.sparta.outsideworld.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByUserIdAndCommentId(Long id, Long commentId);

    List<Like> findByCommentId(Long commentId);

    List<Like> findByPostIdAndCommentId(Long blogId, Long commentId);

    Optional<Object> findByPostId(Long id);

    Like findByUserIdAndPostId(Long id, Long id1);
}
