package com.letscode.moviesBattle.domain.repository.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//TODO: the system is not creating a history of right and wrong answers
public class GameEntity {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    private boolean isActive;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private QuizEntity lastQuizz;

    @Min(value = 0, message = "The numbers of wrong answers must be higher than equals to ZERO")
    private int wrongAnswers;

    @Min(value = 0, message = "The numbers of right answers must be higher than equals to ZERO")
    private int rightAnswers;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private Set<QuizEntity> quizzes;

    public GameEntity(final long id, final UserEntity userEntity, final QuizEntity lastQuizz, final Set<QuizEntity> quizzes) {
        this.id = id;
        this.userEntity = userEntity;
        this.quizzes = quizzes;
        this.lastQuizz = lastQuizz;
        this.isActive = true;
    }
}
