package com.letscode.moviesBattle.domain.repository.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class UserEntityTest {

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
        final var userName = "some username";
        final var password = "some password";
        final var roles = "some roles";
        final var isActive = true;
        final var entity = new UserEntity(1, userName, password, isActive, roles);

        //WHEN
        final var violations = validator.validate(entity);

        //THEN
        assertThat(violations.isEmpty()).isTrue();
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getUserName()).isEqualTo(userName);
        assertThat(entity.getPassword()).isEqualTo(password);
        assertThat(entity.getRoles()).isEqualTo(roles);
        assertThat(entity.isActive()).isEqualTo(isActive);
    }

    @ParameterizedTest
    @MethodSource("invalidFields")
    void invalidFieldsConstructor(final int id, final String userName, final String password, final boolean isActive, final String roles) {
        //GIVEN
        final var entity = new UserEntity(id, userName, password, isActive, roles);

        //WHEN
        final var violations = validator.validate(entity);

        //THEN
        assertThat(violations.isEmpty()).isFalse();
    }

    private static Stream<Arguments> invalidFields() {
        return Stream.of(
                arguments(1, null, "some string", true, "some string"),
                arguments(1, "some string", null, true, "some string"),
                arguments(1, "some string", "some string", true, null)
        );
    }
}