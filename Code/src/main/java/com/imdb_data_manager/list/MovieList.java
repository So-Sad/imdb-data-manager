package main.java.com.imdb_data_manager.list;

import main.java.com.imdb_data_manager.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieList {

    private List<Movie> movies = new ArrayList<>();

    public MovieList() {
    }

    public MovieList(List<Movie> movies) {
        this.movies = movies;
    }

    public boolean addMovie(Movie movie){
        if(movies.contains(movie)){
            return false;
        }
        else {
            movies.add(movie);
            return true;
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
