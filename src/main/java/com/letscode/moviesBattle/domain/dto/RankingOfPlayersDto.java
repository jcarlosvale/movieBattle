package com.letscode.moviesBattle.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingOfPlayersDto {

    private List<PlayerPositionDto> playerPositionDtoList;

}
