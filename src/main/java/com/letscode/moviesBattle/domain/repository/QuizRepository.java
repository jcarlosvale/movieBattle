package com.letscode.moviesBattle.domain.repository;

import com.letscode.moviesBattle.domain.repository.model.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

}
