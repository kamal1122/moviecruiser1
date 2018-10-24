package com.stackroute.cruiser.moviecruiser.repository;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.repository.MovieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataMongoTest
@WebAppConfiguration

public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;
    Movie movie;

    @Before
    public void setUp()
    {
        movie = new Movie();
        movie.setImdbId("1234");
        movie.setMovieTitle("Dhoni");
        movie.setPosterUrl("FYTvy7v7");
        movie.setRating(4.5);
        movie.setYearOfRelease(2016);

    }

//    @After
//    public void tearDown(){
//
//        userRepository.deleteAll();
//    }


    @Test
    public void testSaveMovie(){
        movieRepository.save(movie);
        Movie fetchmovie = movieRepository.findById(movie.getImdbId()).get();
        Assert.assertEquals("1234",fetchmovie.getImdbId());

    }

    @Test
    public void testSaveUserFailure() {
        Movie testMovie = new Movie("12345", "john", "ct", 2.8, 2011);
        movieRepository.save(movie);
        Movie fetchmovie = movieRepository.findById(movie.getImdbId()).get();
        Assert.assertNotSame(movie, fetchmovie);
    }

    @Test
    public void testGetAllUser(){
        Movie u = new Movie("1254","Jenny","vyv",4.5,2002);
        Movie u1 = new Movie("1264","Jenny","f7f7",4.9,2003);
        movieRepository.save(u);
        movieRepository.save(u1);

        List<Movie> list = movieRepository.findAll();
        Assert.assertEquals("Jenny",list.get(0).getMovieTitle());




    } @Test
    public void testSearch(){
        movieRepository.save(movie);
        Movie movie1=movieRepository.findBymovieTitle(movie.getMovieTitle()).get(0);
        Movie movie2= movieRepository.findById(movie.getImdbId()).get();
        Assert.assertEquals(movie.toString(),movie1.toString());
        Assert.assertEquals(movie.toString(),movie2.toString());
    }

    @Test
    public void testDeleteMovie(){
        movieRepository.save(movie);
        movieRepository.delete(movie);
        Assert.assertEquals(Optional.empty(),movieRepository.findById(movie.getImdbId()));
    }


}
