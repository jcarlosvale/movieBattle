package com.letscode.moviesBattle.domain.repository.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Table
@Data
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final int id;

    @NotNull
    @Size(max=50, message = "The size of this field must be lower than equals to 50")
    private final String userName;

    @NotNull
    @Size(max=50, message = "The size of this field must be lower than equals to 50")
    private final String password;

    private final boolean active;

    @NotNull
    @Size(max=50, message = "The size of this field must be lower than equals to 50")
    private final String roles;

}