package com.letscode.moviesBattle.domain.repository;

import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, String> {

    Page<MovieEntity> findAll(Pageable pageable);
}
