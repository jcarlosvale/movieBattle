package com.letscode.moviesBattle.domain.repository;

import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String> {

}
