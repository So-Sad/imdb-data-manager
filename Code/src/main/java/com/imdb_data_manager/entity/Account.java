package main.java.com.imdb_data_manager.entity;

import main.java.com.imdb_data_manager.list.Rating;
import main.java.com.imdb_data_manager.list.Watchlist;

public class Account {

    private String login;
    private String password;
    private Watchlist watchlist;
    private Rating rating;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
