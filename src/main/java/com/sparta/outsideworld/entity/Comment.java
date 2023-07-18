package com.sparta.outsideworld.entity;

import com.sparta.outsideworld.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

//    @Column(name = "likes", nullable = false)
//    private int likeCount = 0;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Post post;

//    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
//    private List<Like> likes = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, User user, Post post) {
        this.comment = requestDto.getComment();
        this.user = user;
        this.post = post;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }

//    public void increaseLikeCount(){
//        this.likeCount++;
//    }

//    public void decreaseLikeCount(){
//        this.likeCount--;
//    }
}
