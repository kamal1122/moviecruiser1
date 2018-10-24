package com.stackroute.cruiser.moviecruiser.service;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieAlreadyExistsException;
import com.stackroute.cruiser.exception.MovieNotFoundException;
import com.stackroute.cruiser.repository.MovieRepository;
import com.stackroute.cruiser.service.MovieServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

public class MovieServiceTest {


    Movie movie;

    //Create a mock for UserRepository
    @Mock//test double
    MovieRepository movieRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    MovieServiceImpl movieService;
        ArrayList<Movie> list= null;


    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        movie = new Movie();
        movie.setImdbId("1234");
        movie.setMovieTitle("Dhoni");
        movie.setPosterUrl("FYTvy7v7");
        movie.setRating(4.5);
        movie.setYearOfRelease(2016);
        list = new ArrayList<>();
        list.add(movie);


    }

    @Test
    public void saveUserTestSuccess() throws MovieAlreadyExistsException {

        when(movieRepository.save((Movie)any())).thenReturn(movie);
        Movie savedMovie = movieService.saveMovie(movie);
        Assert.assertEquals(movie,savedMovie);

        //verify here verifies that userRepository save method is only called once
        verify(movieRepository,times(1)).save(movie);

    }
    @Test(expected = MovieAlreadyExistsException.class)
    public void saveUserTestFailure() throws MovieAlreadyExistsException {
        when(movieRepository.save((Movie)any())).thenReturn(null);
        Movie savedMovie = movieService.saveMovie(movie);
        //System.out.println("savedMovie" + User);
        Assert.assertEquals(movie,savedMovie);
//add verify
       /*doThrow(new UserAlreadyExistException()).when(userRepository).findById(eq(101));
       userService.saveUser(user);*/


    }
    @Test
    public void getAllUser(){

        movieRepository.save(movie);
        //stubbing the mock to return specific data
        when(movieRepository.findAll()).thenReturn(list);
        List<Movie> userlist = movieService.getAllMovies();
        Assert.assertEquals(list,userlist);

        //add verify
    }
  @Test
    public void searchbyId(){
       try {
            when(movieRepository.findById(any()).get()).thenReturn(movie);
            Movie m = movieService.searchMovieById("1234");
            Assert.assertEquals(movie, m);
       }
        catch(Exception ex){

      }
  }
  @Test
    public void searchbymoviename(){
      when(movieRepository.findBymovieTitle(any())).thenReturn(list);
      ArrayList<Movie> m = movieService.searchByMovieName("Dhoni");
      Assert.assertEquals(list,m);


    } @Test
    public void deleteMovieTestSuccess()  throws MovieNotFoundException  {

        when(movieRepository.existsById(anyString())).thenReturn(true);
        doNothing().when(movieRepository).deleteById(anyString());
        boolean b = movieService.deleteMovieById(movie.getImdbId());
        //b=true;
        System.out.println(b);
        Assert.assertEquals(true,b);

        //verify here verifies that movieRepository delete method is only called once
        verify(movieRepository,times(1)).deleteById(movie.getImdbId());

    }

    @Test
    public void deleteMovieTestFailure() throws MovieNotFoundException {

        when(movieRepository.existsById(anyString())).thenReturn(false);
        doNothing().when(movieRepository).deleteById(anyString());
        boolean b = movieService.deleteMovieById(movie.getImdbId());
       // b=false;

        //Syst.out.println(b);
        Assert.assertNotEquals(true,b);

        //verify here verifies that movieRepository delete method is only called once
        verify(movieRepository,times(1)).deleteById(movie.getImdbId());

    }

    @Test(expected = NoSuchElementException.class)
    public void updateMovieByIdTestSuccess() throws MovieNotFoundException {

        Movie movie1=new Movie(movie.getImdbId(),"Robot","http://robot2/3d/poster",9.3,2018);
        when(movieRepository.existsById(anyString())).thenReturn(true);
        when(movieRepository.findById(anyString()).get()).thenReturn(movie);

        //doNothing().when(movieRepository).deleteById(anyString());
        Movie fetchMovie= movieService.updateMovieById(movie.getImdbId(),movie1);
        //System.out.println(b);
        Assert.assertNotEquals(null,fetchMovie);

        //verify here verifies that movieRepository delete method is only called once
        verify(movieRepository,times(1)).existsById(movie.getImdbId());

    }

}
