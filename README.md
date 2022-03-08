# Welcome to the Movies Battle API

This project contains a REST api that provides functionality for a card game, where two movies are given and the player has to guess the one with the best IMDB rating (https://www.imdb.com/)  

## Requirements

### Player must be logged

You can use the pairs "user1" and "pass1".<br>
We are using an H2 database to store the Authentication Data, via Basic Auth and applying the Spring Security functionality. <br>
The endpoints used by the game path must be authenticated (_startGame_, _nextQuiz_, _stopGame_ and _ranking_).

### Quiz generation

Using real data, when the application starts, it reads a file provided by IMDb Datasets (https://www.imdb.com/interfaces/) , containing the movies IDs. In a random way, it is selected a set of movie's ids to be retrieved of the API (http://www.omdbapi.com/) <br>
Each game contains a set of Quiz (pair of movies A-B), and the following generation rules:<br>
1. It is not allowed to have the same movie in the pair A-B.
2. It is not allowed to repeat the same PAIR of movies in a game like: A-B, B-A.
3. It is not possible to generate a new quiz if exists a previous one not answered yet.

### Game Rules

1. Max. erros in a game: 3. After this limit is reached, it is necessary to stop the current game.
2. It is possible to play only one game. It means is not possible to start a game if you have another started. You must stop the previous one.
3. You only can stop a game if you have one started.

### Ranking

You can retrieve the TOP players recorded in the application.

## API Documentation:
First start the application and visit:
http://localhost:8080/swagger-ui/

Login/password: _user1 / pass1_

### Endpoints

####START A GAME

_POST /v1/game/startGame_ 

####NEXT QUIZ

_POST /v1/game/nextQuiz_

####STOP GAME

_POST /v1/game/stopGame_

####RANKING

_GET /v1/game/ranking/{top}_

## How project is organized

* Maven
* Java 11
* SpringBoot 2.X
* JUnit 5 - Unit and Integration tests
* H2 database

## Main frameworks

* Spring Web
* Spring Data
* Spring Security
* OpenAPI 3.0

## Commands:

To run:

    mvn spring-boot:run

To compile, test:

    mvn clean install