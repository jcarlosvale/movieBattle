package com.letscode.moviesBattle.domain.repository.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

import java.util.Set;
import java.util.stream.Stream;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
        final var lastQuizz = mock(QuizzEntity.class);
        final var quizzes = Set.of(mock(QuizzEntity.class));

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
        assertThat(entity.getLastQuizz()).isEqualTo(lastQuizz);
        assertThat(entity.getQuizzes()).isEqualTo(quizzes);
    }

    @ParameterizedTest
    @MethodSource("invalidFields")
    void invalidFieldsConstructor(final UserEntity user, final QuizzEntity lastQuizz, final Set<QuizzEntity> quizzes) {
        //GIVEN
        final var entity = new GameEntity(1, user, lastQuizz, quizzes);

        //WHEN
        final var violations = validator.validate(entity);

        //THEN
        assertThat(violations.isEmpty()).isFalse();
    }

    private static Stream<Arguments> invalidFields() {
        return Stream.of(
                arguments(null, mock(QuizzEntity.class), Set.of(mock(QuizzEntity.class))),
                arguments(mock(UserEntity.class), null, Set.of(mock(QuizzEntity.class))),
                arguments(mock(UserEntity.class), mock(QuizzEntity.class), null)
        );
    }
}