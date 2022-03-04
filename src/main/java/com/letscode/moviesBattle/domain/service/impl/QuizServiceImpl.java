package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.repository.QuizRepository;
import com.letscode.moviesBattle.domain.repository.model.QuizEntity;
import com.letscode.moviesBattle.domain.service.MovieService;
import com.letscode.moviesBattle.domain.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final MovieService movieService;
    private final QuizRepository quizRepository;

    @Override
    public QuizEntity generateQuizCandidate() {
        return QuizEntity.builder()
                .movieOne(movieService.getRandomMovie())
                .movieTwo(movieService.getRandomMovie())
                .build();
    }
}
