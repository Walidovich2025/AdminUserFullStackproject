package com.example.fawry.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fawry.entity.Movie;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByImdbId(String imdbId);
    Page<Movie> findAll(Pageable pageable);
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable); 


}
