package com.letscode.moviesBattle.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDto {

    private long id;
    private MovieDto movieOne;
    private MovieDto movieTwo;
    private int rightAnswers;
    private int wrongAnswers;

}
