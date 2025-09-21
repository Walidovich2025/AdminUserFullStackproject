package com.example.fawry.controller;


import com.example.fawry.entity.Movie;
import com.example.fawry.service.MovieService;
import com.example.fawry.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class MovieController {
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private OmdbService omdbService;
    
    // Admin endpoints
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/movies/omdb")
    public ResponseEntity<?> searchOmdbMovies(
            @RequestParam String title,
            @RequestParam(defaultValue = "1") int page) {
        try {
            Map<String, Object> result = omdbService.searchMovies(title, page);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error searching movies: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/movies")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        if (movieService.existsByImdbId(movie.getImdbId())) {
            return ResponseEntity.badRequest().body("Movie already exists in database");
        }
        
        Movie savedMovie = movieService.saveMovie(movie);
        return ResponseEntity.ok(savedMovie);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted successfully");
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/movies/batch")
    public ResponseEntity<?> addMovies(@RequestBody Movie[] movies) {
        for (Movie movie : movies) {
            if (!movieService.existsByImdbId(movie.getImdbId())) {
                movieService.saveMovie(movie);
            }
        }
        return ResponseEntity.ok("Movies added successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/movies/batch")
    public ResponseEntity<?> deleteMovies(@RequestBody Long[] ids) {
        for (Long id : ids) {
            movieService.deleteMovie(id);
        }
        return ResponseEntity.ok("Movies deleted successfully");
    }
    
    // User endpoints
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user/movies")
    public ResponseEntity<Page<Movie>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieService.getAllMovies(pageable);
        return ResponseEntity.ok(movies);
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user/movies/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user/movies/search")
    public ResponseEntity<Page<Movie>> searchMovies(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieService.searchMovies(query, pageable);
        return ResponseEntity.ok(movies);
    }
}
