package com.letscode.moviesBattle.domain.repository.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

class GameEntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validConstructor() {
        //GIVEN
        final var id = 1;
        final var user = mock(UserEntity.class);
        final var isActive = true;
        final var lastQuizz = mock(QuizEntity.class);
        final var quizzes = Set.of(mock(QuizEntity.class));

        final var entity = new GameEntity(id, user, lastQuizz, quizzes);

        //WHEN
        final var violations = validator.validate(entity);

        //THEN
        assertThat(violations.isEmpty()).isTrue();
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getUserEntity()).isEqualTo(user);
        assertThat(entity.isActive()).isEqualTo(isActive);
        assertThat(entity.getWrongAnswers()).isEqualTo(0);
        assertThat(entity.getRightAnswers()).isEqualTo(0);
        assertThat(entity.getLastQuiz()).isEqualTo(lastQuizz);
        assertThat(entity.getQuizzes()).isEqualTo(quizzes);
    }

    @ParameterizedTest
    @MethodSource("invalidFields")
    void invalidFieldsConstructor(final UserEntity user, final QuizEntity lastQuizz, final Set<QuizEntity> quizzes) {
        //GIVEN
        final var entity = new GameEntity(1, user, lastQuizz, quizzes);

        //WHEN
        final var violations = validator.validate(entity);

        //THEN
        assertThat(violations.isEmpty()).isFalse();
    }

    private static Stream<Arguments> invalidFields() {
        return Stream.of(
                arguments(null, mock(QuizEntity.class), Set.of(mock(QuizEntity.class))),
                arguments(mock(UserEntity.class), null, Set.of(mock(QuizEntity.class))),
                arguments(mock(UserEntity.class), mock(QuizEntity.class), null)
        );
    }
}