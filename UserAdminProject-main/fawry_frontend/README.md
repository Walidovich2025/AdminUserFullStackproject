# Fawry Frontend - Movie Database App

This is an Angular 18 application for managing a movie database with user authentication and role-based access control.

## Features

- **User Authentication**: Login and registration system
- **Role-based Access**: Admin and regular user roles
- **Movie Management**:
  - Admins can search OMDB API and add movies to database
  - Admins can delete movies
  - Users can view and rate movies
- **Responsive Design**: Built with Bootstrap 5

## Technologies Used

- Angular 18.2.0
- TypeScript 5.5.0
- Bootstrap 5.3.3
- RxJS 7.8.0

## Getting Started

### Prerequisites

- Node.js (v20.15.0 or higher)
- npm (v10.7.0 or higher)

### Installation

1. Clone the repository
2. Install dependencies:
   ```bash
   npm install
   ```

### Development Server

Run the development server:

```bash
npm start
# or
npx ng serve
```

The application will be available at `http://localhost:4200`

### Build

Build the project for production:

```bash
npm run build
# or
npx ng build
```

## Project Structure

- `src/app/components/` - Angular components
  - `login/` - Login component
  - `register/` - Registration component
  - `admin-dashboard/` - Admin dashboard for managing movies
  - `user-dashboard/` - User dashboard for viewing movies
  - `movie-details/` - Movie details and rating component
- `src/app/services/` - Angular services
  - `auth.service.ts` - Authentication service
  - `movie.service.ts` - Movie management service
- `src/app/guards/` - Route guards
  - `auth.guard.ts` - Authentication guard
  - `admin.guard.ts` - Admin role guard
- `src/app/models/` - TypeScript interfaces
- `src/app/interceptors/` - HTTP interceptors

## API Endpoints

The application expects a backend API running on:

- Authentication: `http://localhost:8087/api/auth`
- Movies: `http://localhost:3000/api`

## User Roles

- **Admin (ROLE_ADMIN)**: Can search OMDB, add/delete movies
- **User**: Can view movies, rate movies, and view movie details

## Development Notes

- The application uses Angular 18 with strict TypeScript configuration
- Bootstrap 5 is used for styling
- JWT tokens are used for authentication
- Form validation is implemented with Angular Reactive Forms
