package main.java.com.imdb_data_manager.list;

import main.java.com.imdb_data_manager.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class Rating {

    private List<Movie> movies = new ArrayList<>();

    public Rating(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
