package com.letscode.moviesBattle.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "movies")
@Data
public class MoviesBattleProperties {

    private String apiKey;
    private int maxToLoad;
    private String gzipMoviesFile;
}
