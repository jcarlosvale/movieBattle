package com.letscode.moviesBattle.domain.repository;

import com.letscode.moviesBattle.domain.repository.model.GameEntity;
import com.letscode.moviesBattle.domain.repository.projection.RankingProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<GameEntity, Long> {

    String GET_RANKING =
            "select \n"
                    + "\trownum() as position, "
                    + "\tu.user_name as user, "
                    + "\tsum(g.right_answers) as points \n"
                    + "\tfrom user u\n"
                    + "\tjoin game g on (g.user_entity_id = u.id)\n"
                    + "\tgroup by u.user_name\n"
                    + "\torder by points asc\n"
                    + "\tlimit :top";
    @Query(value = GET_RANKING, nativeQuery = true )
    List<RankingProjection> getRanking(@Param("top") int topPlayers);

    Optional<GameEntity> findGameEntityByUserEntityIdAndActiveTrue(long userId);
}
