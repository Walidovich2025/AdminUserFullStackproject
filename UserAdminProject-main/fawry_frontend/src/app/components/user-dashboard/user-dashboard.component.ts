import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Router } from "@angular/router";
import { MovieService } from "../../services/movie.service";

@Component({
  selector: "app-user-dashboard",
  templateUrl: "./user-dashboard.component.html",
  styleUrls: ["./user-dashboard.component.scss"],
})
export class UserDashboardComponent implements OnInit {
  movies: any[] = [];
  loading = false;
  error = "";
  searchForm!: FormGroup;
  currentPage = 1;
  totalPages = 0;

  constructor(
    private movieService: MovieService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      searchTerm: [""],
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

  viewDetails(id: string): void {
    this.router.navigate(["/movie", id]);
  }

  search(): void {
    const searchTerm = this.searchForm.value.searchTerm;
    if (!searchTerm) {
      this.loadMovies();
      return;
    }

    this.loading = true;
    this.movieService.searchMovies(searchTerm).subscribe({
      next: (response) => {
        this.movies = response.movies;
        this.currentPage = response.currentPage;
        this.totalPages = response.totalPages;
        this.loading = false;
      },
      error: (error) => {
        this.error = "Failed to search movies";
        this.loading = false;
      },
    });
  }

  changePage(page: number): void {
    if (page > 0 && page <= this.totalPages) {
      this.loadMovies(page);
    }
  }
}
