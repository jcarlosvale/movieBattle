package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.repository.MovieRepository;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import com.letscode.moviesBattle.domain.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieEntity getRandomMovie() {

        final int count = (int) movieRepository.count();
        final int randomPage = new Random().nextInt(count);
        return movieRepository
                .findAll(PageRequest.of(randomPage, 1))
                .getContent()
                .get(0);
    }
}
