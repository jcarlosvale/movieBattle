package com.letscode.moviesBattle.domain.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;


@Entity
@Table(name = "MOVIE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieEntity {

    @Id
    @NotNull
    @Size(max=100)
    private String imdbID;

    @NotNull
    @Size(max=100)
    private String title;

    @NotNull
    @DecimalMin(value = "0.00", message = "The rating must be between [0-10]")
    @DecimalMax(value = "10.00", message = "The rating must be between [0-10]")
    private double imdbRating;

    @NotNull
    @Min(value = 0, message = "The numbers of votes must be higher than equals to ZERO")
    private long imdbVotes;
}
