INSERT INTO USER (id, user_name, password, active, roles)
VALUES
    (1, 'user1', 'pass1', true, 'USER'),
    (2, 'user2', 'pass2', true, 'USER'),
    (3, 'user3', 'pass3', true, 'ADMIN');

INSERT INTO MOVIE (IMDBID, IMDB_RATING, IMDB_VOTES, TITLE)
VALUES
    ('IMDB1', 1.5, 10, 'MOVIE 1'),
    ('IMDB2', 2.5, 10, 'MOVIE 2'),
    ('IMDB3', 3.5, 10, 'MOVIE 3'),
    ('IMDB4', 4.5, 10, 'MOVIE 4'),
    ('IMDB5', 5.5, 10, 'MOVIE 5');