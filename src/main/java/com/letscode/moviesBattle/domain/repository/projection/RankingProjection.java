package com.letscode.moviesBattle.domain.repository.projection;

public interface RankingProjection {
    int getPosition();
    String getUser();
    int getPoints();
}
