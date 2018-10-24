package com.stackroute.cruiser.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.Entity;
//import javax.persistence.Id;

@Document(collection="movie")
public class Movie {
    @Id
    private String imdbId;
    private String movieTitle;
    private String posterUrl;
    private double rating;
    private int yearOfRelease;

    public Movie() {
    }

    public Movie(String imdbId, String movieTitle, String posterUrl, double rating, int yearOfRelease) {
        this.imdbId = imdbId;
        this.movieTitle = movieTitle;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.yearOfRelease = yearOfRelease;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "Id=" + imdbId +
                ", movieTitle='" + movieTitle + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", rating=" + rating +
                ", yearOfRelease='" + yearOfRelease + '\'' +
                '}';
    }
}
