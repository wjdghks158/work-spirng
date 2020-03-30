 
 
 CREATE TABLE board (
no          INT          AUTO_INCREMENT,
title       VARCHAR(200)    NOT NULL,
writer       VARCHAR(40)    NOT NULL,
content    VARCHAR(4000),
reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
view_cnt    INT DEFAULT 0,
PRIMARY KEY (no)
);

 CREATE TABLE comment (
 no          INT          AUTO_INCREMENT,
writer       VARCHAR(40)    NOT NULL,
content    VARCHAR(4000),
reg_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
boardno    INT NOT NULL,
BOARDWRITER VARCHAR(40) NOT NULL,
PRIMARY KEY (no)
);


 commit;
 
SHOW CREATE TABLE board_file;

CREATE TABLE `board` (
   `NO` int NOT NULL AUTO_INCREMENT,
   `board_no` int DEFAULT NULL,
   `file_ori_name` varchar(200) DEFAULT NULL,
   `file_save_name` varchar(200) DEFAULT NULL,
   `file_size` varchar(512) DEFAULT NULL,
   PRIMARY KEY (`NO`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
 
 
 CREATE TABLE `member` (
   `ID` varchar(20) DEFAULT NULL,
   `NAME` varchar(10) DEFAULT NULL,
   `password` varchar(20) DEFAULT NULL,
   `EMAIL_ID` varchar(20) DEFAULT NULL,
   `EMAIL_DOMAIN` varchar(20) DEFAULT NULL,
   `TEL1` varchar(3) DEFAULT NULL,
   `TEL2` varchar(4) DEFAULT NULL,
   `TEL3` varchar(4) DEFAULT NULL,
   `POST` varchar(5) DEFAULT NULL,
   `BASIC_ADDR` varchar(20) DEFAULT NULL,
   `DETAIL_ADDR` varchar(20) DEFAULT NULL,
   `type` varchar(1) NOT NULL DEFAULT 'U',
   `reg_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
 
 CREATE TABLE `t_board` (
   `NO` int NOT NULL AUTO_INCREMENT,
   `TITLE` varchar(20) DEFAULT NULL,
   `WRITER` varchar(10) DEFAULT NULL,
   `REG_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`NO`)
 ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
 
 CREATE TABLE `board_file` (
   `NO` int NOT NULL AUTO_INCREMENT,
   `board_no` int DEFAULT NULL,
   `file_ori_name` varchar(200) DEFAULT NULL,
   `file_save_name` varchar(200) DEFAULT NULL,
   `file_size` varchar(512) DEFAULT NULL,
   PRIMARY KEY (`NO`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
 
 
 
 create view board_view
as select b.no , b.title, b.writer, b.reg_date, b.view_cnt, count(c.boardno) as  cmt_cnt
from board as  b left join comment  as c on b.no = c.boardno
group by b.no, b.title, b.writer, b.reg_date, b.view_cnt ;


 
