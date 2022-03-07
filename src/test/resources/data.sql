INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (111, 'user111', 'pass111', true, 'USER');

INSERT INTO MOVIE (IMDBID, IMDB_RATING, IMDB_VOTES, TITLE)
VALUES
    ('IMDB1', 1.5, 10, 'MOVIE 1'),
    ('IMDB2', 2.5, 10, 'MOVIE 2'),
    ('IMDB3', 3.5, 10, 'MOVIE 3'),
    ('IMDB4', 4.5, 10, 'MOVIE 4'),
    ('IMDB5', 5.5, 10, 'MOVIE 5');

insert into QUIZ (ID, MOVIE_ONE_IMDBID, MOVIE_TWO_IMDBID)
values
    (111, 'IMDB1', 'IMDB2');

INSERT INTO GAME (ID, ACTIVE, RIGHT_ANSWERS, WRONG_ANSWERS, LAST_QUIZ_ID, USER_ENTITY_ID)
values
    (111, true, 5, 1, 111, 111);