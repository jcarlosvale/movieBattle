package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.repository.QuizRepository;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
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
        final MovieEntity movieOne = movieService.getRandomMovie();
        MovieEntity movieTwo = movieService.getRandomMovie();
        while(movieTwo.equals(movieOne)) {
            movieTwo = movieService.getRandomMovie();
        }
        return QuizEntity.builder()
                .movieOne(movieOne)
                .movieTwo(movieTwo)
                .build();
    }

    @Override
    public QuizEntity save(QuizEntity quizEntity) {
        return quizRepository.save(quizEntity);
    }

    @Override
    public String evaluateWinnerId(final QuizEntity quiz) {
        final double scoreOne = quiz.getMovieOne().getImdbRating() * quiz.getMovieOne().getImdbVotes();
        final double scoreTwo = quiz.getMovieTwo().getImdbRating() * quiz.getMovieTwo().getImdbVotes();
        return scoreOne > scoreTwo ? quiz.getMovieOne().getImdbID() : quiz.getMovieTwo().getImdbID();
    }
}
