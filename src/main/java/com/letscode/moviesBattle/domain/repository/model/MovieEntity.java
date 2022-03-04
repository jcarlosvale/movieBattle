package com.letscode.moviesBattle.domain.repository.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Table
@Data
@AllArgsConstructor
public class MovieEntity {

    @Id
    @NotNull
    @Size(max=100)
    private final String imdbID;

    @NotNull
    @Size(max=100)
    private final String title;

    @NotNull
    @DecimalMin(value = "0.00", message = "The rating must be between [0-10]")
    @DecimalMax(value = "10.00", message = "The rating must be between [0-10]")
    private final double imdbRating;

    @NotNull
    @Min(value = 0, message = "The numbers of votes must be higher than equals to ZERO")
    private final long imdbVotes;

}
