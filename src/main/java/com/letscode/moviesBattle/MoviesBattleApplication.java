package com.letscode.moviesBattle;

import com.letscode.moviesBattle.config.MoviesLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class MoviesBattleApplication implements ApplicationRunner {

	private final MoviesLoader moviesLoader;

	public static void main(String[] args) {
		SpringApplication.run(MoviesBattleApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		moviesLoader.loadMovies();
	}
}
