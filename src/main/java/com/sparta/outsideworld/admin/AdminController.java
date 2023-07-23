package com.sparta.outsideworld.admin;

import com.sparta.outsideworld.dto.*;
import com.sparta.outsideworld.entity.Post;
import com.sparta.outsideworld.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    // 전체 유저 조회
    @Secured("ROLE_ADMIN")
    @GetMapping("/user-list")
    public List<User> getUserList() {
        return adminService.getUserList();
    }

    // 전체 게시글 조회
    @Secured("ROLE_ADMIN")
    @GetMapping("/post-list")
    public List<Post> getPostList() { return adminService.getPostList(); }

    // 유저 프로필 수정
    @Secured("ROLE_ADMIN")
    @PutMapping("/profile/{userid}")
    public ResponseEntity<ApiResponseDto> updateUserProfile(@PathVariable Long userid, @RequestBody ProfileRequestDto profileRequestDto) {
        adminService.updateUserProfile(userid, profileRequestDto);
        return ResponseEntity.ok().body(new ApiResponseDto("유저 정보 수정에 성공했습니다.", HttpStatus.OK.value()));
    }

    // 유저 권한 User 로 변경
    @Secured("ROLE_ADMIN")
    @PutMapping("/user-role/{userid}")
    public ResponseEntity<ApiResponseDto> updateUserRole(@PathVariable Long userid) {
        adminService.updateUserRole(userid);
        return ResponseEntity.ok().body(new ApiResponseDto("유저 정보 수정에 성공했습니다.", HttpStatus.OK.value()));

    }

    // 유저 권한 Admin 으로 변경
    @Secured("ROLE_ADMIN")
    @PutMapping("/admin-role/{userid}")
    public  ResponseEntity<ApiResponseDto> updateAdminRole(@PathVariable Long userid) {
        adminService.updateAdminRole(userid);
        return ResponseEntity.ok().body(new ApiResponseDto("유저 정보 수정에 성공했습니다.", HttpStatus.OK.value()));
    }

    // 게시글 수정
    @Secured("ROLE_ADMIN")
    @PutMapping("/post/{postid}")
    public void updatePost(@PathVariable Long postid, @RequestBody PostRequestDto postRequestDto) {
        adminService.updatePost(postid, postRequestDto);
    }

    // 댓글 수정
    @Secured("ROLE_ADMIN")
    @PutMapping("/comment/{postid}/{commentid}")
    public void updateComment(@PathVariable Long postid, @PathVariable Long commentid, @RequestBody CommentRequestDto commentRequestDto) {
        adminService.updateComment(postid, commentid, commentRequestDto);
    }

    // 유저 삭제
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/user-delete/{userid}")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Long userid) {
        adminService.deleteUser(userid);
        return ResponseEntity.ok().body(new ApiResponseDto("유저 삭제에 성공했습니다.", HttpStatus.OK.value()));
    }

    // 게시글 삭제
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/post-delete/{postid}")
    public void deletePost(@PathVariable Long postid) { adminService.deletePost(postid); }

    // 댓글 삭제
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/comment-delete/{postid}/{commentid}")
    public void deleteComment(@PathVariable Long postid, @PathVariable Long commentid) { adminService.deleteComment(postid, commentid); }
}
