package com.sparta.outsideworld.entity;

import com.sparta.outsideworld.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(name = "likes")
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, User user, Post post, long likeCount) {
        this.comment = requestDto.getComment();
        this.user = user;
        this.post = post;
        this.likeCount = likeCount;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
