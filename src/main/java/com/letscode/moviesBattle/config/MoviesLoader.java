package com.letscode.moviesBattle.config;

import com.letscode.moviesBattle.domain.repository.MovieRepository;
import com.letscode.moviesBattle.domain.repository.model.MovieEntity;
import com.letscode.moviesBattle.scrap.dto.ImdbMovieDto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
@RequiredArgsConstructor
//TODO: remove in the future and implement web scrapper
public class MoviesLoader {


    private final MovieRepository movieRepository;
    private final MoviesBattleProperties moviesBattleProperties;

    public void loadMovies() throws IOException {
        log.info("INSERTING MOVIES TO DATABASE...");
        final List<String> lines = readFileLines();
        final Set<Integer> addedLine = new HashSet<>();
        final Random random = new Random();
        final RestTemplate restTemplate = new RestTemplate();
        while(addedLine.size() < moviesBattleProperties.getMaxToLoad()) {
            int selectedLine = random.nextInt(lines.size())+1;
            if(!addedLine.contains(selectedLine)) {
                String line = lines.get(selectedLine);
                String[] fields = line.split("\t");
                if (fields.length == 3) {
                    String imdbId = fields[0];
                    final ImdbMovieDto imdbMovieDto =
                            restTemplate.getForEntity("https://www.omdbapi.com/?apikey=" + moviesBattleProperties.getApiKey() + "&i=" + imdbId,
                                    ImdbMovieDto.class).getBody();
                    if (Objects.nonNull(imdbMovieDto) && !imdbMovieDto.getTitle().isEmpty()) {
                        movieRepository.save(
                                MovieEntity.builder()
                                        .imdbID(fields[0])
                                        .title(imdbMovieDto.getTitle())
                                        .imdbRating(Double.parseDouble(fields[1]))
                                        .imdbVotes(Long.parseLong(fields[2]))
                                        .build()
                        );
                        addedLine.add(selectedLine);
                        log.info("INSERTED MOVIE #" + addedLine.size() + " TITLE: " + imdbMovieDto.getTitle());
                    }
                }
            }
        }
    }

    private List<String> readFileLines() throws IOException {
        final File file = ResourceUtils.getFile("classpath:"+ moviesBattleProperties.getGzipMoviesFile());
        final InputStream fileStream = new FileInputStream(file);
        final InputStream gzipStream = new GZIPInputStream(fileStream);
        final Reader decoder = new InputStreamReader(gzipStream, StandardCharsets.UTF_8);
        final BufferedReader buffered = new BufferedReader(decoder);
        return buffered.lines().collect(Collectors.toList());
    }
}
