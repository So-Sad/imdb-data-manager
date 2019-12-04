package main.java.com.imdb_data_manager.entity;

import main.java.com.imdb_data_manager.list.MovieList;
import main.java.com.imdb_data_manager.list.Rating;
import main.java.com.imdb_data_manager.list.Watchlist;

public class Account {

    public static String APIKEY = "c712d04b";

    private String nickName;
    private String login; //ur108105157
    private MovieList watchlist;
    private MovieList rating;

    public Account() {
        watchlist = new MovieList();
        rating = new MovieList();
    }

    public Account(String login, MovieList watchlist, MovieList rating) {
        this.login = login;
        this.watchlist = watchlist;
        this.rating = rating;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public MovieList getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(MovieList watchlist) {
        this.watchlist = watchlist;
    }

    public MovieList getRating() {
        return rating;
    }

    public void setRating(MovieList rating) {
        this.rating = rating;
    }
}
