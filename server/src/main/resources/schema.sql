-- Board Table
drop table if exists board;
create table board (
    board_id bigint not null auto_increment primary key,
    writer varchar(100) not null,
    title varchar(100) not null,
    content text not null,
    hits bigint not null default 0,
    reg_date timestamp not null default current_timestamp,
    mod_date timestamp not null default current_timestamp
);