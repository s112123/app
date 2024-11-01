-- drop table
drop table if exists profile_image;
drop table if exists member;
drop table if exists role;

-- role table → role (1)-(*) member
create table role (
    id int not null primary key,
    role char(10) not null unique
);

-- member table
create table member (
    id bigint auto_increment primary key,
    role_id int not null,
    email varchar(50) not null unique,
    username varchar(15) not null,
    password varchar(255) not null,
    status varchar(20) not null default 'OFFLINE',
    reg_date timestamp default current_timestamp,
    mod_date timestamp default current_timestamp on update current_timestamp,
    foreign key (role_id) references role (id) on update cascade
);

-- profile_image table → profile_image (1)-(1) member
create table profile_image (
    id bigint primary key,
    file_path varchar(255) not null,
    original_file_name varchar(255) not null,
    upload_file_name varchar(255) not null,
    extension char(10) not null,
    foreign key (id) references member (id) on delete cascade
);