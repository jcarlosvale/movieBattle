package com.letscode.moviesBattle.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {

    @JsonProperty(required = true)
    private long userId;

    @JsonProperty(required = true)
    private String imdbID;

}
