package com.letscode.moviesBattle.domain.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class RankingOfPlayersDtoTest {

    @Test
    void constructor() {

        //GIVEN
        final var player1 = PlayerPositionDto.builder().position(1).user("user1").points(100).build();
        final var player2 = PlayerPositionDto.builder().position(2).user("user2").points(50).build();
        final var playerPositionDtoList = List.of(player1, player2);

        //WHEN
        final var dto = new RankingOfPlayersDto(playerPositionDtoList);

        //THEN
        assertThat(dto.getPlayerPositionDtoList()).hasSameElementsAs(playerPositionDtoList);
    }
}