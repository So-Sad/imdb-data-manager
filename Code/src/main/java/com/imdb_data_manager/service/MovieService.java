package main.java.com.imdb_data_manager.service;

import main.java.com.imdb_data_manager.list.MovieList;

public class MovieService {

    private MovieList topRated;
    private MovieList comingSoon;
    private MovieList weekly;

    public MovieList collectTopRated() {
        return topRated;
    }

    public MovieList collectComingSoon() {
        return comingSoon;
    }

    public MovieList collectWeekly() {
        return weekly;
    }

    public MovieList getTopRated() {
        return topRated;
    }

    public void setTopRated(MovieList topRated) {
        this.topRated = topRated;
    }

    public MovieList getComingSoon() {
        return comingSoon;
    }

    public void setComingSoon(MovieList comingSoon) {
        this.comingSoon = comingSoon;
    }

    public MovieList getWeekly() {
        return weekly;
    }

    public void setWeekly(MovieList weekly) {
        this.weekly = weekly;
    }
}
