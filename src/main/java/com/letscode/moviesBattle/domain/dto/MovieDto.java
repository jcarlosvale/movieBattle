package com.letscode.moviesBattle.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MovieDto {

    private final String imdbID;
    private final String title;

}
