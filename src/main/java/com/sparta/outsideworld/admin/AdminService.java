package com.sparta.outsideworld.admin;

import com.sparta.outsideworld.dto.*;
import com.sparta.outsideworld.entity.Comment;
import com.sparta.outsideworld.entity.Post;
import com.sparta.outsideworld.entity.User;
import com.sparta.outsideworld.entity.UserRoleEnum;
import com.sparta.outsideworld.repository.CommentRepository;
import com.sparta.outsideworld.repository.PostRepository;
import com.sparta.outsideworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public List<Post> getPostList() { return postRepository.findAll(); }

    public void updateUserProfile(Long userid, ProfileRequestDto profileRequestDto) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        user.setEmail(profileRequestDto.getEmail());
        user.setIntroduction(profileRequestDto.getIntroduction());
        user.setImage(profileRequestDto.getImage());
        userRepository.save(user);
    }

    public void updateUserRole(Long userid) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        user.setRole(UserRoleEnum.USER);
        userRepository.save(user);
    }

    public void updateAdminRole(Long userid) {
        User user = userRepository.findById(userid).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        user.setRole(UserRoleEnum.ADMIN);
        userRepository.save(user);
    }

    public void updatePost(Long postid, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        post.update(postRequestDto);
    }

    public void updateComment(Long postid, Long commentid, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findByIdAndPostId(commentid, postid);
        comment.update(commentRequestDto);
    }

    public ApiResponseDto deleteUser(Long userid) {
        userRepository.deleteById(userid);
        return new ApiResponseDto("삭제가 완료되었습니다.",200);
    }

    public ApiResponseDto deletePost(Long postid) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new NullPointerException("선택하신 게시물은 존재하지 않습니다.")
        );
        postRepository.delete(post);
        return new ApiResponseDto("삭제가 완료되었습니다.",200);
    }

    public ApiResponseDto deleteComment(Long postid, Long commentid) {
        Comment comment = commentRepository.findByIdAndPostId(commentid, postid);
        commentRepository.delete(comment);
        return new ApiResponseDto("삭제가 완료되었습니다.",200);
    }
}
