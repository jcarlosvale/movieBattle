package com.letscode.moviesBattle.domain.service;

import com.letscode.moviesBattle.domain.repository.model.MovieEntity;

public interface MovieService {
    MovieEntity getRandomMovie();
}
