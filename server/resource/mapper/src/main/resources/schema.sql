-- drop table
drop table if exists member_profile;
drop table if exists member_role;
drop table if exists member;
drop table if exists role;

-- role table
create table role (
    id int primary key,
    role char(10) not null
);

-- member table
create table member (
    id bigint auto_increment primary key,
    email varchar(50) not null unique,
    username varchar(20) not null,
    password varchar(255) not null,
    reg_date timestamp default current_timestamp
);

-- member_role table → member (1)-(*) member_role (*)-(1) role
create table member_role (
    member_id bigint not null,
    role_id int not null,
    primary key (member_id, role_id),
    foreign key (member_id) references member (id),
    foreign key (role_id) references role (id)
);

-- member_profile table → member_profile (1)-(1) member
create table member_profile (
    id bigint primary key,
    original_file_name varchar(255) not null,
    upload_file_name varchar(255) not null,
    extension char(10) not null,
    file_path varchar(255),
    foreign key (id) references member (id)
);