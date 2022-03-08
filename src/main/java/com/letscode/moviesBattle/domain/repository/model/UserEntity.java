package com.letscode.moviesBattle.domain.repository.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(max=50, message = "The size of this field must be lower than equals to 50")
    private String userName;

    @NotNull
    @Size(max=250, message = "The size of this field must be lower than equals to 50")
    private String password;

    private boolean active;

    @NotNull
    @Size(max=50, message = "The size of this field must be lower than equals to 50")
    private String roles;
}