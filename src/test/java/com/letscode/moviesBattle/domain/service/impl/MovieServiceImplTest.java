package com.letscode.moviesBattle.domain.service.impl;

import com.letscode.moviesBattle.domain.repository.MovieRepository;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private Page<MovieEntity> page;
    @Mock
    private MovieEntity movieEntity;

    @InjectMocks
    private MovieServiceImpl service;

    @Test
    void getRandomMovie() {
        //GIVEN
        given(movieRepository.count())
                .willReturn(1L);
        given(movieRepository.findAll(PageRequest.of(0,1)))
                .willReturn(page);
        given(page.getContent())
                .willReturn(List.of(movieEntity));

        //WHEN
        final var entity = service.getRandomMovie();

        //THEN
        assertThat(entity).isEqualTo(movieEntity);
    }

}