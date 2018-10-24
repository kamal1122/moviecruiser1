package com.stackroute.cruiser.controller;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(@Qualifier("Implementation2") MovieService movieService) {
        this.movieService = movieService;
        System.out.println(movieService.getClass().getName());
    }

    @PostMapping("movie")
    public ResponseEntity<?> saveMovie(@Valid @RequestBody Movie movie){
        ResponseEntity responseEntity;
        try{
            movieService.saveMovie(movie);
            responseEntity= new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);

        }catch (MovieAlreadyExistsException e){
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping (value = "movie")
    public ResponseEntity<?> getAllMovies(){
        return new ResponseEntity<List<Movie>>(movieService.getAllMovies(),HttpStatus.OK);
    }

    @GetMapping(path = "/imdbId/{id}")
    public ResponseEntity<?> searchById(@PathVariable("id") String id){
       /* ResponseEntity responseEntity;
        try{
            Movie movie= movieService.searchMovieById(id);
            responseEntity= new ResponseEntity<String>(movie.toString(),HttpStatus.FOUND);
        }catch (Exception e){
            responseEntity= new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
        }
        return responseEntity;*/
       return new ResponseEntity<Movie>(movieService.searchMovieById(id),HttpStatus.OK);
    }

    @GetMapping("movieName/{movieName}")
    public ResponseEntity<?> searchByName(@PathVariable("movieName") String movieName){
        ResponseEntity responseEntity;
        try{
            List<Movie> movie= movieService.searchByMovieName(movieName);
            responseEntity= new ResponseEntity<List<Movie>>(movie,HttpStatus.FOUND);
        }catch (Exception e){
            responseEntity= new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @DeleteMapping("movie/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        ResponseEntity responseEntity;
        try{
            movieService.deleteMovieById(id);
            responseEntity= new ResponseEntity<String>("Successfully deleted",HttpStatus.OK);
        }catch (Exception e){
            responseEntity = new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
        }
        return  responseEntity;
    }

    @PutMapping("movie/{id}")
    public ResponseEntity<?> updateMovieById(@Valid@RequestBody Movie movie ,@PathVariable("id") String id){
        ResponseEntity responseEntity;
        try {
            movieService.updateMovieById(id,movie);
            responseEntity = new ResponseEntity<String>("Successfully Updated",HttpStatus.CREATED);
        }catch (Exception e){
            responseEntity =new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
}
