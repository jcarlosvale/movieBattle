package com.letscode.moviesBattle.domain.repository.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Set;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MovieEntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("validFields")
    void validConstructor(final String id, final String title, final double rating, final int votes) {
        //GIVEN
        final var entity = new MovieEntity(id, title, rating, votes);

        //WHEN
        Set<ConstraintViolation<MovieEntity>> violations = validator.validate(entity);

        //THEN
        assertThat(violations.isEmpty()).isTrue();
    }

    private static Stream<Arguments> validFields() {
        return Stream.of(
                arguments("", "", 0.00, 0),
                arguments("", "", 10.00, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidFields")
    void invalidFieldsConstructor(final String id, final String title, final double rating, final int votes) {
        //GIVEN
        final var entity = new MovieEntity(id, title, rating, votes);

        //WHEN
        Set<ConstraintViolation<MovieEntity>> violations = validator.validate(entity);

        //THEN
        assertThat(violations.isEmpty()).isFalse();
    }

    private static Stream<Arguments> invalidFields() {
        return Stream.of(
                arguments(null, "some string", 5.00, 10),
                arguments("some string", null, 5.00, 10),
                arguments("some string", "some string", -0.01, 10),
                arguments("some string", "some string", 10.01, 10),
                arguments("some string", "some string", 5.00, -1)
        );
    }

}