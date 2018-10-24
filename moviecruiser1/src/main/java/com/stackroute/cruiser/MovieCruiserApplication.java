package com.stackroute.cruiser;

import com.stackroute.cruiser.domain.Movie;
import com.stackroute.cruiser.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


@SpringBootApplication

public class MovieCruiserApplication implements ApplicationListener<ContextRefreshedEvent>, CommandLineRunner {

    private MovieRepository movieRepository;

    @Autowired
    public MovieCruiserApplication(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(MovieCruiserApplication.class, args);
	}
    @Override
    public void run(String... args) throws Exception {

        Movie m1=new Movie("tt22015","Dosti","http://dosti.jpg",7.4f,2012);
        //movieRepository.save(m1);
        Movie m2=new Movie("tt23226","Dhoom","http://ddlj.jpg",9.5f,2014);
        //movieRepository.save(m2);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Movie m1=new Movie("tt23025","Faasle","http://fasle.jpg",5.2f,2004);
       // movieRepository.save(m1);
        Movie m2=new Movie("tt23026","Yaariyan","http://yaariyan.jpg",7.2f,2014);
      //  movieRepository.save(m2);


    }


}
