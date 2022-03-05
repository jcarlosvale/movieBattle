package com.letscode.moviesBattle;

import com.letscode.moviesBattle.config.MoviesBattleProperties;
import com.letscode.moviesBattle.domain.repository.MovieRepository;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import com.letscode.moviesBattle.scrap.dto.ImdbMovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MoviesBattleApplication implements ApplicationRunner {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MoviesBattleProperties properties;

	public static void main(String[] args) {
		SpringApplication.run(MoviesBattleApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		final File file = ResourceUtils.getFile("classpath:title.ratings.tsv.gz");
		final InputStream fileStream = new FileInputStream(file);
		final InputStream gzipStream = new GZIPInputStream(fileStream);
		final Reader decoder = new InputStreamReader(gzipStream, StandardCharsets.UTF_8);
		final BufferedReader buffered = new BufferedReader(decoder);
		final List<String> lines = buffered.lines().collect(Collectors.toList());
		final Set<Integer> addedLine = new HashSet<>();
		final Random random = new Random();
		final RestTemplate restTemplate = new RestTemplate();
		while(addedLine.size() < properties.getMaxToLoad()) {
			int selectedLine = random.nextInt(lines.size())+1;
			if(!addedLine.contains(selectedLine)) {
				String line = lines.get(selectedLine);
				String[] fields = line.split("\t");
				String imdbId = fields[0];
				final ImdbMovieDto imdbMovieDto =
						restTemplate.getForEntity("https://www.omdbapi.com/?apikey=a7c0b9a6&i=" + imdbId, ImdbMovieDto.class).getBody();
				movieRepository.save(
						MovieEntity.builder()
								.imdbID(fields[0])
								.title(imdbMovieDto.getTitle())
								.imdbRating(Double.parseDouble(fields[1]))
								.imdbVotes(Long.parseLong(fields[2]))
								.build()
				);
				addedLine.add(selectedLine);
			}
		}
	}
}
