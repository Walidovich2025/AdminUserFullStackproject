import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../models/movie.model';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private apiUrl = 'http://localhost:3000/api';

  constructor(private http: HttpClient) { }

  getMovies(page: number = 1): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/movies?page=${page}`);
  }

  getMovieById(id: string): Observable<Movie> {
    return this.http.get<Movie>(`${this.apiUrl}/movies/${id}`);
  }

  searchMovies(query: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/movies/search?title=${query}`);
  }

  searchOMDB(title: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/movies/omdb?title=${title}`);
  }

  addMovie(movie: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/movies`, movie);
  }

  deleteMovie(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/movies/${id}`);
  }

  rateMovie(id: string, rating: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/movies/${id}/rate`, { rating });
  }
}