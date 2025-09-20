package com.example.fawry.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.fawry.entity.Movie;
import com.example.fawry.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    
    private final MovieRepository movieRepository;
    
    
    
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }
    
    public Page<Movie> searchMovies(String query, Pageable pageable) {
        return movieRepository.findByTitleContainingIgnoreCase(query, pageable);
    }
    
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }
    
    public Optional<Movie> getMovieByImdbId(String imdbId) {
        return movieRepository.findByImdbId(imdbId);
    }
    
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
    
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
    
    public boolean existsByImdbId(String imdbId) {
        return movieRepository.findByImdbId(imdbId).isPresent();
    }
}
