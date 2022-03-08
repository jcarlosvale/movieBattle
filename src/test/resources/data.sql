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