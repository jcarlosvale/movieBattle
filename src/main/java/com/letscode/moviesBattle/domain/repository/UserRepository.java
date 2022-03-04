package com.letscode.moviesBattle.domain.repository;

import com.letscode.moviesBattle.domain.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
