create table users
(
    id            bigint auto_increment
        primary key,
    created_at    datetime(6)            null,
    modified_at   datetime(6)            null,
    email         varchar(255)           not null,
    profile_image varchar(255)           null,
    introduction  varchar(500)           null,
    kakao_id      bigint                 null,
    old_password1 varchar(255)           null,
    old_password2 varchar(255)           null,
    password      varchar(255)           not null,
    role          enum ('ADMIN', 'USER') null,
    username      varchar(255)           not null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UK_r43af9ap4edm43mmtq01oddj6
        unique (username)
);

INSERT INTO outsideworld.users (id, created_at, modified_at, email, profile_image, introduction, kakao_id, old_password1, old_password2, password, role, username) VALUES (1, '2023-07-24 08:05:26.375789', '2023-07-24 08:05:26.375789', 'hakjun123@gmail.com', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfHv0HVlndqwOD6P6vXPE2P7OibzDYavCerALlPIZkFHn21juZCV5J7RGLFvtv3GhLt9c&usqp=CAU', '안녕하세요 주학준입니다. 잘 부탁드립니다. 밖에서 오늘 뭐하지? 웹사이트 많이 이용해 주세요!', null, null, null, '$2a$10$kAHRYgaITDfLr5oypSH2e.dyc/l9lB6orGpDjVUHZad6kHIUc69/q', 'ADMIN', 'hakjun123');
INSERT INTO outsideworld.users (id, created_at, modified_at, email, profile_image, introduction, kakao_id, old_password1, old_password2, password, role, username) VALUES (2, '2023-07-24 08:06:33.595919', '2023-07-24 10:58:46.640095', 'shm@naver.com', null, '안녕안녕44', null, null, null, '$2a$10$M2ZBgwE0BzVtotD42UFtSe3wI8z.J/Am1GLEOUlbpsmKtvtKAX3ci', 'ADMIN', 'sungmin123');
INSERT INTO outsideworld.users (id, created_at, modified_at, email, profile_image, introduction, kakao_id, old_password1, old_password2, password, role, username) VALUES (3, '2023-07-24 08:07:34.996606', '2023-07-24 08:45:42.109166', 'hyerin123@gmail.com', 'https://t1.daumcdn.net/cfile/tistory/992FA9345AB64A0D19', '안녕하세요 조혜린입니다. 잘 부탁드립니다. 밖에서 오늘 뭐하지? 웹사이트 많이 이용해 주세요!', null, null, null, '$2a$10$Qg4TZFedYowafoBE1Vesp.RkLNl0tgMpm5GpOUZWtr92zfEtn8u6G', 'USER', 'hyerin123');
INSERT INTO outsideworld.users (id, created_at, modified_at, email, profile_image, introduction, kakao_id, old_password1, old_password2, password, role, username) VALUES (4, '2023-07-24 08:18:53.889019', '2023-07-24 08:47:47.656299', 'gunwook123@gmail.com', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyZ2aIlKyvXrO-PyLBAISO8vSradzzCcUcjQ&usqp=CAU', '안녕하세요 남건욱입니다. 잘 부탁드립니다. 밖에서 오늘 뭐하지? 웹사이트 많이 이용해 주세요!', null, null, null, '$2a$10$CuPYyUaouVXkUyT6q6TbD.bMV3TPeSHXffNaXS2CE4N9MzXxTvZe6', 'USER', 'gunwook123');
INSERT INTO outsideworld.users (id, created_at, modified_at, email, profile_image, introduction, kakao_id, old_password1, old_password2, password, role, username) VALUES (5, '2023-07-24 08:31:29.328447', '2023-07-24 08:31:29.328447', 'pureum123@gmail.com', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmud6isU7RPsgKzGFK4p_LwJtFUnR11hykvg&usqp=CAU', '안녕하세요 고푸름입니다. 잘 부탁드립니다. 밖에서 오늘 뭐하지? 웹사이트 많이 이용해 주세요!', null, null, null, '$2a$10$q1Tf1m/N0MALi6JZxCiMh.ttBX3jSgle.pOiGbnBRIdBTkM.tJgHO', 'USER', 'pureum123');
