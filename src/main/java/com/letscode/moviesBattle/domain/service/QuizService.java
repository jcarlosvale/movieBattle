package com.letscode.moviesBattle.domain.service;

import com.letscode.moviesBattle.domain.repository.model.QuizEntity;

public interface QuizService {
    QuizEntity generateQuizCandidate();

    QuizEntity save(QuizEntity quizEntity);

    String evaluateWinnerId(QuizEntity quiz);
}
