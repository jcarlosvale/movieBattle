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

INSERT INTO GAME_QUIZZES(GAME_ENTITY_ID, QUIZZES_ID)
values
    (111, 111);

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

INSERT INTO GAME_QUIZZES(GAME_ENTITY_ID, QUIZZES_ID)
values
    (112, 112);

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

INSERT INTO GAME_QUIZZES(GAME_ENTITY_ID, QUIZZES_ID)
values
    (114, 114);

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

INSERT INTO GAME_QUIZZES(GAME_ENTITY_ID, QUIZZES_ID)
values
    (115, 115);

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

INSERT INTO GAME_QUIZZES(GAME_ENTITY_ID, QUIZZES_ID)
values
    (116, 116);

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

INSERT INTO GAME_QUIZZES(GAME_ENTITY_ID, QUIZZES_ID)
values
    (117, 117),
    (117, 118);

--DATA USER TO STOP GAME
INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (119, 'user119', 'pass119', true, 'USER');

insert into QUIZ (ID, MOVIE_ONE_IMDBID, MOVIE_TWO_IMDBID)
values
    (119, 'IMDB1', 'IMDB2');

INSERT INTO GAME (ID, ACTIVE, RIGHT_ANSWERS, WRONG_ANSWERS, LAST_QUIZ_ID, USER_ENTITY_ID)
values
    (119, true, 5, 1, 119, 119);

INSERT INTO GAME_QUIZZES(GAME_ENTITY_ID, QUIZZES_ID)
values
    (119, 119);

--DATA USED TO RANKING TEST
INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (120, 'user120', 'pass120', true, 'USER'),
    (121, 'user121', 'pass121', true, 'USER'),
    (122, 'user122', 'pass122', true, 'USER');

insert into QUIZ (ID, MOVIE_ONE_IMDBID, MOVIE_TWO_IMDBID)
values
    (120, 'IMDB1', 'IMDB2'),
    (121, 'IMDB1', 'IMDB2'),
    (122, 'IMDB1', 'IMDB3'),
    (123, 'IMDB2', 'IMDB3'),
    (124, 'IMDB1', 'IMDB2'),
    (125, 'IMDB2', 'IMDB3');

INSERT INTO GAME (ID, ACTIVE, RIGHT_ANSWERS, WRONG_ANSWERS, LAST_QUIZ_ID, USER_ENTITY_ID)
values
    (120, false, 10, 3, 120, 120),
    (121, false, 10, 3, 121, 121),
    (122, false, 20, 2, 123, 121),
    (123, false, 20, 3, 125, 122);

INSERT INTO GAME_QUIZZES(GAME_ENTITY_ID, QUIZZES_ID)
values
    (120, 120),
    (121, 121),
    (122, 122),
    (122, 123),
    (123, 124),
    (123, 125);
