package com.sparta.outsideworld.dto;

import com.sparta.outsideworld.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private Long id; // 게시글 번호
    private String username; // 유저이름
    private String title; // 게시글 제목
    private String contents; // 게시글 내용
    private Integer likeCount; // 좋아요 수
    private LocalDateTime createAt; // 게시글 생성시간
    private LocalDateTime modifiedAt; // 게시글 수정시간
    // private List<CommentResponseDto> commentList; // 게시글에 포함된 댓글목록


    public PostResponseDto(Post post){
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.likeCount = post.getLikeList().size();
        this.createAt = post.getCreatedTime();
        this.modifiedAt = post.getCreatedTime();
        // 댓글목록
        // this.commentList = post.getCommentList().stream()
        //        .map(CommentResponseDto::new).collect(Collectors.toList());
    }

}