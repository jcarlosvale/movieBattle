package com.letscode.moviesBattle.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerDto {

    private long gameId;
    private String imdbID;

}