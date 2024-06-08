create table about
(
    id          int auto_increment
        primary key,
    content     text     not null,
    create_time datetime not null,
    update_time datetime null,
    create_user int      not null comment '创建者id'
)
    comment '关于信息';

create table attachment
(
    id          int auto_increment
        primary key,
    type        varchar(50)  not null comment '文件类型',
    url         varchar(255) not null comment '文件url(默认存储在minio)',
    create_time datetime     not null comment '上传日期'
)
    comment '附件表';

create table authority
(
    id          int auto_increment
        primary key,
    name        varchar(50)                        not null comment '权限名',
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime                           null
)
    comment '权限表（可以执行各种操作）';

create table category
(
    id            int auto_increment
        primary key,
    category_name varchar(20)                        not null comment '分类名(必须唯一)',
    create_time   datetime default CURRENT_TIMESTAMP not null,
    update_time   datetime                           null,
    create_user   int                                not null comment '创建者id',
    constraint category_pk
        unique (category_name)
)
    comment '文章分类表';

create table friend_link
(
    id   int auto_increment
        primary key,
    name varchar(50)  not null comment '友链名称',
    url  varchar(255) not null comment '链接'
)
    comment '友链表';

create table menu
(
    id          int auto_increment comment '菜单表（每个菜单包含多个权限）'
        primary key,
    name        varchar(50)                        not null comment '菜单名',
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime                           null,
    constraint role_menu_pk_2
        unique (id)
)
    comment '角色菜单';

create table other_verify_code
(
    username        varchar(50) not null,
    type            varchar(50) not null,
    code            varchar(10) not null,
    expiration_time datetime    not null,
    primary key (username, type)
);

create table register_verify_code
(
    username        varchar(50)  not null,
    email           varchar(255) not null,
    code            varchar(10)  not null,
    expiration_time datetime     null comment '过期时间',
    primary key (username, email)
);

create table role
(
    id          int auto_increment comment '角色id'
        primary key,
    name        varchar(50)                        not null comment '角色名称',
    is_disable  tinyint  default 0                 not null comment '是否禁用(1禁用 0启用)',
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime                           null,
    constraint user_role_pk_2
        unique (id),
    constraint user_role_pk_3
        unique (name)
);

create table role_authority
(
    id           int auto_increment
        primary key,
    role_id      int not null comment '角色id',
    authority_id int not null comment '权限id',
    constraint role_authority_authority_id_fk
        foreign key (authority_id) references authority (id),
    constraint role_authority_role_id_fk
        foreign key (role_id) references role (id)
)
    comment '角色与权限绑定';

create table tag
(
    id          int auto_increment
        primary key,
    name        varchar(50) not null comment '标签名',
    create_time datetime    not null,
    update_time datetime    null,
    create_user int         not null comment '创建者id',
    constraint tag_pk
        unique (name)
)
    comment '文章标签';

create table topic
(
    id   int auto_increment
        primary key,
    name varchar(50) not null comment '话题名'
)
    comment '说说的话题';

create table user
(
    id          int auto_increment comment '用户id'
        primary key,
    username    varchar(20)  not null comment '用户名、唯一',
    password    varchar(40)  not null comment '密码',
    nickname    varchar(50)  null comment '昵称',
    email       varchar(255) not null comment '邮箱',
    avatar      varchar(255) null comment '头像',
    create_time datetime     not null comment '注册时间',
    update_time datetime     null comment '更新时间',
    signature   varchar(255) null comment '个性签名',
    role        int          not null comment '用户角色(一个用户只能一种角色)',
    state       varchar(20)  not null,
    constraint user_pk2
        unique (id),
    constraint user_pk3
        unique (username),
    constraint user_user_role_id_fk
        foreign key (role) references role (id)
);

create table article
(
    id               int auto_increment
        primary key,
    user_id          int                      not null comment '作者id',
    category_id      int                      null comment '分类id',
    article_cover    varchar(1024)            null comment '文章封面图片',
    article_title    varchar(50)              not null comment '文章标题',
    article_abstract varchar(500)             null comment '文章摘要',
    article_content  longtext                 not null comment '正文',
    is_delete        tinyint                  not null comment '是否删除(1 删除 0 未删除)',
    status           tinyint                  not null comment '状态值 1公开 2私密 3草稿',
    type             tinyint                  not null comment '⽂章类型 1原创 2转载 3翻 译',
    look_num         int      default 0       not null comment '文章访问次数',
    password         varchar(255)             null comment '访问密码',
    origin_url       varchar(255)             null comment '原文链接',
    create_time      datetime default (now()) not null,
    update_time      datetime                 null,
    constraint article_category_id_fk
        foreign key (category_id) references category (id),
    constraint article_user_id_fk
        foreign key (user_id) references user (id)
)
    comment '文章表';

create table article_tag
(
    id         int auto_increment
        primary key,
    article_id int not null comment '文章id',
    tag_id     int not null comment '标签id',
    constraint article_tag_article_id_fk
        foreign key (article_id) references article (id),
    constraint article_tag_tag_id_fk
        foreign key (tag_id) references tag (id)
)
    comment '文章和标签绑定';

create table comment
(
    id                int auto_increment
        primary key,
    user_id           int                      not null comment '用户id(谁发的评论)',
    content           text                     not null comment '评论内容',
    parent_comment_id int                      null comment '这条评论归属的父评论id(多级嵌套的评论)(留空表示是最高层评论)',
    type              tinyint                  not null comment '评论类型 1.⽂章 2.留 ⾔ 3.关于我 4.友链 5. 说说',
    parent_id         int                      not null comment '评论所属文章或说说的id',
    is_delete         tinyint                  not null comment '是否被删除(1 删除 0 未删除)',
    is_review         tinyint                  not null comment '是否审核(0未审核 1审核通过 2审核不通过)',
    create_time       datetime default (now()) not null,
    update_time       datetime                 null,
    constraint comment_user_id_fk
        foreign key (user_id) references user (id)
)
    comment '文章评论表';

create table notice
(
    id          int auto_increment
        primary key,
    title       varchar(50) null comment '通知标题',
    content     text        not null comment '通知内容',
    create_time datetime    not null,
    update_time datetime    null,
    create_user int         not null,
    constraint notice_user_id_fk
        foreign key (create_user) references user (id)
)
    comment '系统通知';

create table talk
(
    id          int auto_increment
        primary key,
    user_id     int                                not null comment '说说发布者id',
    title       varchar(50)                        not null comment '说说标题',
    content     varchar(2000)                      not null comment '说说内容',
    status      tinyint                            not null comment '状态 1.公开 2.私密',
    look_num    int      default 0                 not null comment '被访问次数',
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime                           null,
    constraint talk_user_id_fk
        foreign key (user_id) references user (id)
);

create table talk_topic
(
    id       int auto_increment
        primary key,
    talk_id  int not null,
    topic_id int not null,
    constraint talk_topic_talk_id_fk
        foreign key (talk_id) references talk (id),
    constraint talk_topic_topic_id_fk
        foreign key (topic_id) references topic (id)
)
    comment '说说和话题绑定';

create table website_config
(
    id          int auto_increment
        primary key,
    config      varchar(2000) null,
    create_time datetime      not null,
    update_time datetime      null
)
    comment '网站配置信息表';

