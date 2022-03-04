package com.letscode.moviesBattle.domain.repository.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizzEntity {

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
        QuizzEntity that = (QuizzEntity) o;
        return
                this.movieOne.equals(that.movieOne) && this.movieTwo.equals(that.movieTwo) ||
                this.movieOne.equals(that.movieTwo) && this.movieTwo.equals(that.movieOne);
    }
}
