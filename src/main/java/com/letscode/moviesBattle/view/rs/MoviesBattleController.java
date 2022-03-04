package com.letscode.moviesBattle.view.rs;

import com.letscode.moviesBattle.domain.dto.GameDto;
import org.springframework.http.ResponseEntity;

public interface MoviesBattleController {

    ResponseEntity<GameDto> startGame();

    ResponseEntity<GameDto> nextQuizz(long gameId, String imdbID);

    ResponseEntity<GameDto> endGame();

}
