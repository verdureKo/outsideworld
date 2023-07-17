package com.sparta.outsideworld.entity;

import com.sparta.outsideworld.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(length = 500)
    private String contents;

//    @Column
//    private Long likeCount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // post를 연관관계의 주인으로 설정. post 엔티티 제거시 연관된 comment 제거.
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    // post를 연관관계의 주인으로 설정. post 엔티티 제거시 연관된 like 제거.
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Like> likeList = new ArrayList<>();

    public void update(PostRequestDto postRequestDto, User user){
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }


}
