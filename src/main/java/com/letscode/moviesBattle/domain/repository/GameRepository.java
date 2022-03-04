package com.letscode.moviesBattle.domain.repository;

import com.letscode.moviesBattle.domain.repository.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<GameEntity, Long> {

    Optional<GameEntity> findGameEntityByUserEntityIdAAndActiveTrue(long userId);

}
