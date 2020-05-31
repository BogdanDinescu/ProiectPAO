CREATE TABLE Films(
    film_id number(6,0) not NULL primary key,
    name varchar(25) NOT NULL,
    year number(4,0),
    director varchar(25),
    rating varchar(5),
    genre varchar(25),
    sub_genre varchar(25),
    extra_info varchar(25)
    );

SELECT * FROM FILMS;

DROP TABLE FILMS;

SELECT COUNT(*) NR FROM FILMS;

INSERT INTO FILMS (film_id,name,year,director,rating,genre,sub_genre,extra_info)
VALUES(2,'Star Wars - A new Hope',1977,'George Lucas','AP12','SF','Space Opera','RayPunk');


CREATE TABLE Program(
  program_id number(6,0) not null,
  start_date Date not null,
  theatre_id number(4,0) not null,
  film_id number(6,0) not null
);

DROP TABLE Program;

ALTER TABLE PROGRAM
ADD CONSTRAINT PK_PROGRAM PRIMARY KEY (program_id);

ALTER TABLE PROGRAM
ADD CONSTRAINT FK_FILM_ID FOREIGN KEY (FILM_ID) REFERENCES FILMS (FILM_ID);

ALTER TABLE PROGRAM
ADD CONSTRAINT UNIQ_DATE_THTR UNIQUE(START_DATE,THEATRE_ID);

SELECT PROGRAM_ID, START_DATE, NAME, FLOOR(START_DATE-SYSDATE)+1 DAY 
FROM PROGRAM JOIN FILMS USING(FILM_ID) 
WHERE START_DATE BETWEEN SYSDATE AND SYSDATE+7 ORDER BY START_DATE;

SELECT * FROM PROGRAM;

UPDATE PROGRAM
SET START_DATE = TO_DATE('31-05-2020 21:00','dd-MM-yyyy HH24:MI')
WHERE PROGRAM_ID = 1;

INSERT INTO PROGRAM(program_id,start_date,theatre_id,film_id)
VALUES (1,to_date('31-05-2020 3:00 PM','DD-MM-YYYY HH:MI PM'),1,2);

SELECT START_DATE, NAME, ROUND(START_DATE-SYSDATE) +1 DAY
FROM PROGRAM JOIN FILMS USING(FILM_ID)
WHERE START_DATE BETWEEN SYSDATE AND SYSDATE+7
ORDER BY START_DATE;

CREATE TABLE TICKETS(
  ticket_id number(6,0) not null,
  program_id number(6,0) not null,
  row_num number(2,0) not null,
  seat_num number(2,0) not null
);

ALTER TABLE TICKETS
ADD CONSTRAINT PK_TICKET PRIMARY KEY (ticket_id);

ALTER TABLE TICKETS
ADD CONSTRAINT FK_PROGRAM_ID FOREIGN KEY (program_id) REFERENCES PROGRAM;

ALTER TABLE TICKETS
ADD CONSTRAINT UNIQUE_PROG_SEAT UNIQUE (program_id,row_num,seat_num);

INSERT INTO TICKETS(ticket_id,program_id,row_num,seat_num)
VALUES(1,1,2,4);

DELETE FROM TICKETS;

SELECT * FROM TICKETS;

DELETE FROM TICKETS;

DROP TABLE TICKETS;
