create table likes
(
    id         bigint auto_increment
        primary key,
    comment_id bigint null,
    post_id    bigint null,
    user_id    bigint null,
    constraint FKe4guax66lb963pf27kvm7ikik
        foreign key (comment_id) references comments (id),
    constraint FKnvx9seeqqyy71bij291pwiwrg
        foreign key (user_id) references users (id),
    constraint FKry8tnr4x2vwemv2bb0h5hyl0x
        foreign key (post_id) references posts (id)
);

INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (1, null, 1, 5);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (2, null, 4, 5);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (3, null, 6, 5);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (4, null, 2, 4);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (5, null, 5, 4);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (6, null, 7, 4);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (7, null, 3, 1);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (8, null, 9, 1);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (9, 2, 4, 3);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (10, 4, 2, 3);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (11, 3, 6, 3);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (12, null, 10, 3);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (13, null, 12, 3);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (14, 1, 1, 4);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (15, null, 8, 4);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (16, null, 12, 4);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (17, 8, 5, 4);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (18, null, 3, 2);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (19, 12, 3, 2);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (20, null, 6, 2);
INSERT INTO outsideworld.likes (id, comment_id, post_id, user_id) VALUES (21, 5, 5, 2);
