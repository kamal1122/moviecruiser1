package com.stackroute.cruiser.service;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;

import java.util.List;

public interface MovieService {
    Movie saveMovie(Movie movie) throws MovieAlreadyExistsException;
    Movie searchMovieById(String id);
    boolean deleteMovieById(String id);
    Movie updateMovieById(String id,Movie movie);
    List<Movie> searchByMovieName(String movieName);
    List<Movie> getAllMovies();
}
