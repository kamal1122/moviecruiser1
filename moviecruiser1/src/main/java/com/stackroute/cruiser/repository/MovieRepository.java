package com.stackroute.cruiser.repository;

//import com.stackroute.cruiser.domain.Movie;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import com.stackroute.cruiser.domain.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie,String> {

//    @Query("SELECT movie FROM Movie movie where movie.movieTitle = :movieTitle")
    ArrayList<Movie> findBymovieTitle(String movieTitle);
}
