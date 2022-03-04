package com.letscode.moviesBattle.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class MovieDto {

    private final String imdbID;
    private final String title;

}
