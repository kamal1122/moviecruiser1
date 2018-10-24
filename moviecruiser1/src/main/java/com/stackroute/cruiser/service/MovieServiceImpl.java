package com.stackroute.cruiser.service;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("Implementation1")
@Primary
public class MovieServiceImpl implements MovieService {
    private static final Logger logger= LoggerFactory.getLogger(MovieServiceImpl.class);

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie saveMovie(Movie movie) throws MovieAlreadyExistsException {
        if(movieRepository.existsById(movie.getImdbId())){
            logger.info("exception ocuured");
            throw new MovieAlreadyExistsException("Movie already Exists");
        }
        Movie saveMovie= movieRepository.save(movie);
        if(saveMovie==null)
            throw new MovieAlreadyExistsException("Movie already Exists");
               logger.warn("exception ocuured");
        return saveMovie;
    }

    @Override
    public Movie searchMovieById(String id) {
        Movie movie= movieRepository.findById(id).get();
        return movie;
    }

    @Override
    public boolean deleteMovieById(String id) {

        if (movieRepository.existsById(id))
            return false;
        movieRepository.deleteById(id);
    return true;
    }

    @Override
    public Movie updateMovieById(String id,Movie movie1) {
        Movie movie=movieRepository.findById(id).get();
        movie.setMovieTitle(movie1.getMovieTitle());
        movie.setPosterUrl(movie1.getPosterUrl());
        movie.setRating(movie1.getRating());
        movie.setYearOfRelease(movie1.getYearOfRelease());
        Movie movie2=movieRepository.save(movie);
        return movie2;
    }

    @Override
    public ArrayList<Movie> searchByMovieName(String movieName) {
        ArrayList<Movie> movie=movieRepository.findBymovieTitle(movieName);
        return movie;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
