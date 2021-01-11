--삭제&수정
drop table users;
delete from users;

drop sequence seq_user_no;

rollback;
commit;

--테이블 생성
create table users (
    no number(10),
    id varchar2(20) UNIQUE not null,
    password varchar2(20) not null,
    name varchar2(20),
    gender varchar2(10),
    primary key(no)
);

--시퀀스 생성
create sequence seq_user_no
INCREMENT by 1
start with 1
nocache;

--test insert
insert into users
VALUES(seq_user_no.nextval, 'asd1234', '5149', '김독자', 'male');


--데이터 확인
select  no,
        id,
        password,
        name,
        gender
from users;
