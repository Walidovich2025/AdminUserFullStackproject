import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { MovieService } from "../../services/movie.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "../../services/auth.service";

@Component({
  selector: "app-movie-details",
  templateUrl: "./movie-details.component.html",
  styleUrls: ["./movie-details.component.scss"],
})
export class MovieDetailsComponent implements OnInit {
  movie: any = null;
  loading = false;
  error = "";
  ratingForm!: FormGroup;
  ratingSubmitted = false;
  ratingSuccess = "";

  constructor(
    private route: ActivatedRoute,
    private movieService: MovieService,
    private formBuilder: FormBuilder,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.ratingForm = this.formBuilder.group({
      rating: [
        "",
        [Validators.required, Validators.min(1), Validators.max(10)],
      ],
    });

    this.route.paramMap.subscribe((params) => {
      const id = params.get("id");
      if (id) {
        this.loadMovie(id);
      }
    });
  }

  loadMovie(id: string): void {
    this.loading = true;
    this.movieService.getMovieById(id).subscribe({
      next: (movie) => {
        this.movie = movie;
        this.loading = false;
      },
      error: (error) => {
        this.error = "Failed to load movie details";
        this.loading = false;
      },
    });
  }

  submitRating(): void {
    this.ratingSubmitted = true;

    if (this.ratingForm.invalid) {
      return;
    }

    this.loading = true;
    const rating = this.ratingForm.value.rating;

    this.movieService.rateMovie(this.movie._id, rating).subscribe({
      next: () => {
        this.ratingSuccess = "Rating submitted successfully";
        this.loading = false;
        this.loadMovie(this.movie._id); // Reload to get updated ratings
        setTimeout(() => (this.ratingSuccess = ""), 3000);
      },
      error: (error) => {
        this.error = "Failed to submit rating";
        this.loading = false;
      },
    });
  }
}
