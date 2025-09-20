import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MovieService } from "../../services/movie.service";

@Component({
  selector: "app-admin-dashboard",
  templateUrl: "./admin-dashboard.component.html",
  styleUrls: ["./admin-dashboard.component.scss"],
})
export class AdminDashboardComponent implements OnInit {
  searchForm!: FormGroup;
  movies: any[] = [];
  loading = false;
  error = "";
  success = "";
  currentPage = 1;
  totalPages = 0;

  constructor(
    private formBuilder: FormBuilder,
    private movieService: MovieService
  ) {}

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      title: ["", Validators.required],
    });

    this.loadMovies();
  }

  loadMovies(page: number = 1): void {
    this.loading = true;
    this.movieService.getMovies(page).subscribe({
      next: (response) => {
        this.movies = response.movies;
        this.currentPage = response.currentPage;
        this.totalPages = response.totalPages;
        this.loading = false;
      },
      error: (error) => {
        this.error = "Failed to load movies";
        this.loading = false;
      },
    });
  }

  searchMovie(): void {
    if (this.searchForm.invalid) {
      return;
    }

    this.loading = true;
    this.movieService.searchOMDB(this.searchForm.value.title).subscribe({
      next: (response) => {
        if (response.Response === "True") {
          this.movies = [response];
          this.error = "";
        } else {
          this.error = response.Error || "No movies found";
          this.movies = [];
        }
        this.loading = false;
      },
      error: (error) => {
        this.error = "Failed to search movies";
        this.loading = false;
      },
    });
  }

  addToDatabase(movie: any): void {
    this.loading = true;
    this.movieService.addMovie(movie).subscribe({
      next: () => {
        this.success = `Movie "${movie.Title}" added successfully`;
        this.loading = false;
        setTimeout(() => (this.success = ""), 3000);
      },
      error: (error) => {
        this.error = `Failed to add movie: ${error}`;
        this.loading = false;
      },
    });
  }

  deleteMovie(id: string): void {
    if (confirm("Are you sure you want to delete this movie?")) {
      this.loading = true;
      this.movieService.deleteMovie(id).subscribe({
        next: () => {
          this.success = "Movie deleted successfully";
          this.loadMovies(this.currentPage);
        },
        error: (error) => {
          this.error = "Failed to delete movie";
          this.loading = false;
        },
      });
    }
  }

  changePage(page: number): void {
    if (page > 0 && page <= this.totalPages) {
      this.loadMovies(page);
    }
  }
}
