package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieBattleServiceImpl {

    private final MovieRepository movieRepository;

    public void test() {
        movieRepository.count();

    }

}
