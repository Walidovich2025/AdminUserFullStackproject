package com.example.fawry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
     private String imdbId;

    private String title;
    private String genre;
    private String duration; // duration in minutes
    private String director;
    private String releaseYear;
    private String  rating; // e.g., IMDb rating
    private String description;
    private String posterUrl; // URL to the movie poster image
    private String type;
    private String plot;// brief summary or plot of the movie

}
