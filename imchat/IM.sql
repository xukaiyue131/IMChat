create database IM;
use IM;
create table user(
	id char(19) primary key comment '用户id',
	nickname varchar(32) not null comment '用户昵称',
	sex int(1) not null comment '用户性别',
	password varchar(64) not null comment '用户密码',
	avater varchar(255) comment '用户头像',
	address varchar(32) comment '用户籍贯',
	level int(2) default 0 comment '用户级别',
	number varchar(64) not null comment '用户号',
	content varchar(255) comment '个性签名',
	email varchar(32) not null comment '用户邮箱',
	create_time datetime not null comment '创建时间'
)default charset=utf8; 

create table friend(
	id char(19) primary key comment 'id',
	userid char(19) not null comment '用户id',
	friendid char(19) not null comment '好友id',
	content varchar(64)  comment '备注',
	create_time datetime not null comment '添加时间'	
)default charset=utf8;

create table apply(
	id char(19) primary key comment 'id',
	apply_id char(19) not null comment '申请者id',
	target_id char(19) not null comment '被申请者id',
	create_time datetime not null comment '申请时间'
)default charset=utf8;

create table user_group(
	id char(19) primary key comment '群聊id',
	nickname varchar(64) comment '群昵称',
	number int not null comment '群人数',
	create_time datetime not null comment '创建时间'
)default charset=utf8;

create table group_number(
	id char(19) primary key comment '群聊id',
	number_id char(19) not null comment '成员id',
	content varchar(64) comment '群备注',
	level int not null default '0',
	create_time datetime not null comment '加入时间'
)default charset=utf8;

create table user_message(
	id char(19) primary key comment '消息id',
	userid char(19) not null comment '发送者id',
	friendid char(19) not null comment '接受者id',
	content varchar(255) not null comment '内容',
	create_time datetime not null comment '发送时间'
)default charset=utf8;

create table group_message(
	id char(19) primary key comment '群聊消息id',
	userid char(19) not null comment '发送者id',
	groupid char(19) not null comment '群聊id',
	content varchar(255) not null comment '内容',
	create_time datetime not null comment '发送时间'
)default charset=utf8;