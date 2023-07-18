package com.sparta.outsideworld.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_Id", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_Id", nullable = false)
    private User following;

    public Follow(User follower, User following){
        this.follower = follower;
        this.following = following;
    }
}
