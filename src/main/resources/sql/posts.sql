create table posts
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    contents    varchar(500) null,
    image       varchar(255) null,
    like_count  bigint       null,
    title       varchar(255) null,
    user_id     bigint       not null,
    constraint FK5lidm6cqbc7u4xhqpxm898qme
        foreign key (user_id) references users (id)
);

INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (1, '2023-07-24 08:09:19.496061', '2023-07-24 08:34:09.255735', '친구따라갔다 건강당하고 왔는데 상쾌했어요', 'https://images.unsplash.com/photo-1682695794947-17061dc284dd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1920&q=80', 1, '바위산 추천합니다!', 3);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (2, '2023-07-24 08:11:45.560869', '2023-07-24 08:39:50.401426', '열기구를 체험했는데 너무 아름다웠어요!', 'https://images.unsplash.com/photo-1684863941689-2962fb53bc0e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDN8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=1920&q=60', 1, '카파도키아 추천합니다', 2);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (3, '2023-07-24 08:20:16.173932', '2023-07-24 08:51:56.044493', '맑은 날 가는것을 추천합니다', 'https://images.unsplash.com/photo-1688317220306-2976558c7a2f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1920&q=80', 2, '야간 산행 추천합니다', 4);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (4, '2023-07-24 08:21:20.052398', '2023-07-24 08:34:43.661023', '곧 잠긴다는 이탈리아의 수상도시 베네치아 추천합니다', 'https://images.unsplash.com/photo-1676020490501-d1969426ad5d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDR8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=1920&q=60', 1, '베네치아 추천', 4);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (5, '2023-07-24 08:23:02.641483', '2023-07-24 10:59:25.856512', '아이유 뮤비에도 나온 부라노섬 추천합니다 알록달록 유리공예도 볼수있어요 근데 많이 비쌈', ' https://images.unsplash.com/photo-1686082939197-606444ac63b7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDIwfHx8ZW58MHx8fHx8&auto=format&fit=crop&w=1920&q=60', 1, '부라노 섬 추천', 3);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (6, '2023-07-24 08:27:19.224775', '2023-07-24 08:52:27.962534', '남극에 가면 우리들의 친구 펭구인을 만날 수 있습니다.', 'https://images.unsplash.com/photo-1687622577762-2c693481b4fb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1920&q=80', 2, '펭귄의 고향', 1);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (7, '2023-07-24 08:28:38.642750', '2023-07-24 08:40:32.042569', '우리가 지금까지 본 바다는 파란색이 아니였던 겁니다', 'https://images.unsplash.com/photo-1687620975279-c0f4e33e8770?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1920&q=80', 1, '맘마미아 그리스', 1);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (8, '2023-07-24 08:32:30.432622', '2023-07-24 08:48:10.747673', '하늘과 땅의 경계가 모호한 우유니 사막 꼭 가보고싶습니다', 'https://images.unsplash.com/photo-1583901370237-2befec865f88?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8JUVDJTlBJUIwJUVDJTlDJUEwJUVCJThCJTg4fGVufDB8fDB8fHww&auto=format&fit=crop&w=1920&q=60', 1, '볼리비아 우유니 사막', 5);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (9, '2023-07-24 08:33:49.928736', '2023-07-24 08:43:46.465480', '미국 교포 코스프레를 하며 스케이트보드를 타는 상상을 합니다', 'https://images.unsplash.com/photo-1687684422877-2cc358c18028?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDE5MnxGem8zenVPSE42d3x8ZW58MHx8fHx8&auto=format&fit=crop&w=1920&q=60', 1, 'LA 기열', 5);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (10, '2023-07-24 08:37:06.152138', '2023-07-24 08:46:41.982642', '프랑스로가면 본토 바게트를 맛보세요!', 'https://images.unsplash.com/photo-1431274172761-fca41d930114?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2670&q=80', 1, '불란서 바게뜨', 4);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (11, '2023-07-24 08:39:24.474061', '2023-07-24 08:39:24.474061', '인생샷 보장되있는 갸리브해 바닷가', 'https://images.unsplash.com/photo-1609166519415-0900a86f4e85?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8JUVDJUI5JUI0JUVCJUE2JUFDJUVCJUI4JThDJUVEJTk1JUI0fGVufDB8fDB8fHww&auto=format&fit=crop&w=1920&q=60', null, '카리브해', 4);
INSERT INTO outsideworld.posts (id, created_at, modified_at, contents, image, like_count, title, user_id) VALUES (12, '2023-07-24 08:42:32.489336', '2023-07-24 08:48:24.308194', '한번쯤 가봐도 좋은 경험이다', 'https://images.unsplash.com/photo-1535190923871-9e9d9a2e1f2a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTA0fHwlRUMlODIlQUMlRUIlQTclODl8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=1920&q=60', 2, '사막', 1);
