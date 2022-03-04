package com.letscode.moviesBattle.domain.service;

import com.letscode.moviesBattle.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieBattleService {

    private final MovieRepository movieRepository;

    public void test() {
        movieRepository.count();

    }

}
