create table comments
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    comment     varchar(255) not null,
    like_count  bigint       null,
    post_id     bigint       not null,
    user_id     bigint       not null,
    constraint FK8omq0tc18jd43bu5tjh6jvraq
        foreign key (user_id) references users (id),
    constraint FKh4c7lvsc298whoyd4w9ta25cr
        foreign key (post_id) references posts (id)
);

INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (1, '2023-07-24 08:34:22.550014', '2023-07-24 08:47:56.843491', '꼭 가보고 싶어요!', 1, 1, 5);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (2, '2023-07-24 08:34:40.917799', '2023-07-24 08:46:19.566302', '물비린내 많이나나요?', 1, 4, 5);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (3, '2023-07-24 08:35:04.939091', '2023-07-24 08:46:33.757747', '끝판왕이시네요!', 1, 6, 5);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (4, '2023-07-24 08:39:48.129299', '2023-07-24 08:46:28.084543', '오오 너무 멋지네요!', 1, 2, 4);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (5, '2023-07-24 08:40:05.012949', '2023-07-24 08:53:14.836496', '아이유 좋습니다', 1, 5, 4);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (6, '2023-07-24 08:40:29.676396', '2023-07-24 08:40:29.676396', '영롱하네요', 0, 7, 4);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (7, '2023-07-24 08:43:04.642312', '2023-07-24 08:43:04.642312', '별 포토샵인가요?', 0, 3, 1);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (8, '2023-07-24 08:43:20.666334', '2023-07-24 08:48:32.841537', '아이유 최고죠', 1, 5, 1);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (9, '2023-07-24 08:43:43.641883', '2023-07-24 08:43:43.641883', '상상만 하세요', 0, 9, 1);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (10, '2023-07-24 08:46:13.290738', '2023-07-24 08:46:13.290738', '많이 날것 같은데요?', 0, 4, 3);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (11, '2023-07-24 08:50:47.681728', '2023-07-24 08:50:47.681728', '물병 정보좀요', 0, 1, 4);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (12, '2023-07-24 08:51:53.320177', '2023-07-24 10:59:50.524483', '제가 가봤는데 포토샵아닙니다 진짜 아닙니다.', 1, 3, 2);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (13, '2023-07-24 08:52:24.765400', '2023-07-24 08:52:24.765400', '오 얼마나 춥나요', 0, 6, 2);
INSERT INTO outsideworld.comments (id, created_at, modified_at, comment, like_count, post_id, user_id) VALUES (14, '2023-07-24 08:53:11.177902', '2023-07-24 08:53:11.177902', '아이유 말이 필요없죠', 0, 5, 2);
