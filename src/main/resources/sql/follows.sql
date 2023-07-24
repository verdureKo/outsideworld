create table follows
(
    id           bigint auto_increment
        primary key,
    follower_id  bigint not null,
    following_id bigint not null,
    constraint FKonkdkae2ngtx70jqhsh7ol6uq
        foreign key (following_id) references users (id),
    constraint FKqnkw0cwwh6572nyhvdjqlr163
        foreign key (follower_id) references users (id)
);

