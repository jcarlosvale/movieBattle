INSERT INTO MOVIE (IMDBID, IMDB_RATING, IMDB_VOTES, TITLE)
VALUES
    ('IMDB1', 1.5, 10, 'MOVIE 1'),
    ('IMDB2', 2.5, 10, 'MOVIE 2'),
    ('IMDB3', 3.5, 10, 'MOVIE 3');

--DATA ACTIVE GAME USER
INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (111, 'user111', 'pass111', true, 'USER');

insert into QUIZ (ID, MOVIE_ONE_IMDBID, MOVIE_TWO_IMDBID)
values
    (111, 'IMDB1', 'IMDB2');

INSERT INTO GAME (ID, ACTIVE, RIGHT_ANSWERS, WRONG_ANSWERS, LAST_QUIZ_ID, USER_ENTITY_ID)
values
    (111, true, 5, 1, 111, 111);

--DATA USER START GAME
INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (112, 'user112', 'pass112', true, 'USER');

insert into QUIZ (ID, MOVIE_ONE_IMDBID, MOVIE_TWO_IMDBID)
values
    (112, 'IMDB1', 'IMDB2');

INSERT INTO GAME (ID, ACTIVE, RIGHT_ANSWERS, WRONG_ANSWERS, LAST_QUIZ_ID, USER_ENTITY_ID)
values
    (112, false, 5, 1, 112, 112);

--DATA USER NOT STARTED GAME
INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (113, 'user113', 'pass113', true, 'USER');

--DATA USER ERROR LIMIT
INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (114, 'user114', 'pass114', true, 'USER');

insert into QUIZ (ID, MOVIE_ONE_IMDBID, MOVIE_TWO_IMDBID)
values
    (114, 'IMDB1', 'IMDB2');

INSERT INTO GAME (ID, ACTIVE, RIGHT_ANSWERS, WRONG_ANSWERS, LAST_QUIZ_ID, USER_ENTITY_ID)
values
    (114, true, 5, 3, 114, 114);

--DATA USER RIGHT ANSWER
INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (115, 'user115', 'pass115', true, 'USER');

insert into QUIZ (ID, MOVIE_ONE_IMDBID, MOVIE_TWO_IMDBID)
values
    (115, 'IMDB1', 'IMDB2');

INSERT INTO GAME (ID, ACTIVE, RIGHT_ANSWERS, WRONG_ANSWERS, LAST_QUIZ_ID, USER_ENTITY_ID)
values
    (115, true, 4, 2, 115, 115);

--DATA USER WRONG ANSWER
INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (116, 'user116', 'pass116', true, 'USER');

insert into QUIZ (ID, MOVIE_ONE_IMDBID, MOVIE_TWO_IMDBID)
values
    (116, 'IMDB1', 'IMDB2');

INSERT INTO GAME (ID, ACTIVE, RIGHT_ANSWERS, WRONG_ANSWERS, LAST_QUIZ_ID, USER_ENTITY_ID)
values
    (116, true, 4, 2, 116, 116);

--DATA USER ONLY ONE QUIZ TO GENERATE
INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (117, 'user117', 'pass117', true, 'USER');

insert into QUIZ (ID, MOVIE_ONE_IMDBID, MOVIE_TWO_IMDBID)
values
    (117, 'IMDB1', 'IMDB3'),
    (118, 'IMDB2', 'IMDB3');

INSERT INTO GAME (ID, ACTIVE, RIGHT_ANSWERS, WRONG_ANSWERS, LAST_QUIZ_ID, USER_ENTITY_ID)
values
    (117, true, 4, 2, 118, 117);