package com.stackroute.cruiser.moviecruiser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.cruiser.controller.MovieController;
import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.exception.MovieNotFoundException;
import com.stackroute.cruiser.service.MovieService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private Movie movie;
    @MockBean
    private MovieService movieService;
    @InjectMocks
    private MovieController movieController;
    private List<Movie> list =null;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        movie = new Movie("testId","testname","testurl",7.9f,2017);
        list = new ArrayList();
        list.add(movie);
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void saveMovie() throws Exception {
        when(movieService.saveMovie(any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void getAllMovies() throws Exception{
        when(movieService.getAllMovies()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void searchById() throws Exception {
        when(movieService.searchMovieById(any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/imdbId/testId")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void searchByName() throws Exception {
        when(movieService.searchByMovieName(any())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movieName/testname")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testUpdateMovieByIdSuccess() throws Exception {
        //movieService.saveMovie(movie);
        // Movie m2=new Movie(movie.getImdbId(),"DDLJ2","http://ddlj2poster/hd/view",9.2,2018);
        when(movieService.updateMovieById((String)any(),(Movie)any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test(expected = MockitoException.class)
    public void testUpdateMovieByIdFailure() throws Exception {
        //movieService.saveMovie(movie);
        when(movieService.updateMovieById((String)any(),(Movie)any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void testDeleteMovieByIdSuccess() throws Exception {
        // movieService.saveMovie(movie);
        when(movieService.deleteMovieById((String)any())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test(expected = MockitoException.class)
    public void testDeleteMovieByIdFailure() throws Exception {
        // movieService.saveMovie(movie);
        when(movieService.deleteMovieById((String)any())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/{id}",movie.getImdbId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}