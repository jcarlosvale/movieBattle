package com.letscode.moviesBattle.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class GameDto {

    private final long id;
    private final MovieDto movieOne;
    private final MovieDto movieTwo;
    private final int rightAnswers;
    private final int wrongAnswers;

}
