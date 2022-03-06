package com.letscode.moviesBattle.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {

    private long userId;
    private MovieDto movieOne;
    private MovieDto movieTwo;
    private int rightAnswers;
    private int wrongAnswers;

}
