package com.letscode.moviesBattle.domain.repository.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "QUIZ")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizEntity {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private MovieEntity movieOne;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private MovieEntity movieTwo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuizEntity that = (QuizEntity) o;
        return movieOne.equals(that.movieOne) && movieTwo.equals(that.movieTwo) || movieOne.equals(that.movieTwo) && movieTwo.equals(that.movieOne) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieOne, movieTwo);
    }
}
