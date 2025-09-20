package com.example.fawry.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.fawry.entity.Movie;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OmdbService {
    
    @Value("${omdb.api.url}")
    private String omdbApiUrl;
    
    @Value("${omdb.api.key}")
    private String omdbApiKey;
    
    private final RestTemplate restTemplate;
    
   
    
    public Map<String, Object> searchMovies(String query, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(omdbApiUrl)
                .queryParam("apikey", omdbApiKey)
                .queryParam("s", query)
                .queryParam("page", page)
                .toUriString();
        
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && "False".equals(response.get("Response"))) {
                throw new RuntimeException("OMDB API Error: " + response.get("Error"));
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to OMDB API: " + e.getMessage(), e);
        }
    }
    
    public Map<String, Object> getMovieDetails(String imdbId) {
        String url = UriComponentsBuilder.fromHttpUrl(omdbApiUrl)
                .queryParam("apikey", omdbApiKey)
                .queryParam("i", imdbId)
                .queryParam("plot", "full")
                .toUriString();
        
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && "False".equals(response.get("Response"))) {
                throw new RuntimeException("OMDB API Error: " + response.get("Error"));
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to OMDB API: " + e.getMessage(), e);
        }
    }
    
   public Movie convertToMovieEntity(Map<String, Object> movieData) {
        Movie movie = new Movie();
        movie.setImdbId((String) movieData.get("imdbID"));
        movie.setTitle((String) movieData.get("Title"));
        movie.setGenre((String) movieData.get("Genre"));
        movie.setDuration((String) movieData.get("Runtime"));
        movie.setDirector((String) movieData.get("Director"));
        movie.setReleaseYear((String) movieData.get("Year"));
        movie.setRating((String) movieData.get("imdbRating"));
        movie.setDescription((String) movieData.get("Plot"));
        movie.setPosterUrl((String) movieData.get("Poster"));
        movie.setType((String) movieData.get("Type"));
        movie.setPlot((String) movieData.get("Plot"));
        return movie;
    }
}