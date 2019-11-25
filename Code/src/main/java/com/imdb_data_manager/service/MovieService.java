package main.java.com.imdb_data_manager.service;

import main.java.com.imdb_data_manager.entity.Account;
import main.java.com.imdb_data_manager.entity.Movie;
import main.java.com.imdb_data_manager.list.MovieList;
import java.io.IOException;
import java.net.URI;


import java.net.http.*;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("Duplicates")
public class MovieService {

    private MovieList topRated;
    private MovieList comingSoon;
    private MovieList weekly;

    public void collectTopRated() {

        String titleID = "";
        MovieList movieList = new MovieList();

        try {
            Document doc = Jsoup.connect("https://www.imdb.com/chart/top").get();
            Elements listRecommended = doc.select("div.wlb_ribbon"); //div.hover-over-image.zero-z-index a[href*=/title/tt]
            for (Element element : listRecommended) {
                titleID = element.attr("data-tconst");

                String uri = "http://www.omdbapi.com/?apikey=" + Account.APIKEY + "&i=" + titleID;
                String responseString = getStringResponse(uri);

                JSONObject movieResponse = new JSONObject(responseString);
                JSONObject ratings = movieResponse.getJSONArray("Ratings").getJSONObject(0);

                Movie movie = new Movie(movieResponse.getString("Title"),
                        movieResponse.getString("Plot"),
                        movieResponse.getString("Genre"),
                        movieResponse.getString("Director"),
                        ratings.getString("Value")
                        );
                movieList.addMovie(movie);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.topRated = movieList;
    }

    public void collectComingSoon() {
        String title = "", name = "";
        MovieList movieList = new MovieList();
        try {
            Document doc = Jsoup.connect("https://www.imdb.com/movies-coming-soon").get();
            Elements listRecommended = doc.select("h4 > a"); //div.hover-over-image.zero-z-index a[href*=/title/tt]
            for (Element element : listRecommended) {
                title = element.attr("title");
                if (!title.isEmpty()) {
                    name = title.substring(0, title.length() - 7);

                    name = name.toLowerCase().replaceAll(" ", "+");
                    String uri = "http://www.omdbapi.com/?apikey=" + Account.APIKEY + "&t=" + name;
                    String responseString = getStringResponse(uri);

                    JSONObject movieResponse = new JSONObject(responseString);
                    JSONObject ratings = movieResponse.getJSONArray("Ratings").getJSONObject(0);

                    Movie movie = new Movie(movieResponse.getString("Title"),
                            movieResponse.getString("Plot"),
                            movieResponse.getString("Genre"),
                            movieResponse.getString("Director"),
                            ratings.getString("Value")
                    );
                    movieList.addMovie(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.comingSoon = movieList;
    }

    public void collectWeekly() {
        String title = "", name = "";
        MovieList movieList = new MovieList();
        try {
            Document doc = Jsoup.connect("https://www.imdb.com/movies-in-theaters").get();
            Elements listRecommended = doc.select("h4 > a"); //div.hover-over-image.zero-z-index a[href*=/title/tt]
            for (Element element : listRecommended) {
                title = element.attr("title");
                if (!title.isEmpty()) {
                    name = title.substring(0, title.length() - 7);

                    name = name.toLowerCase().replaceAll(" ", "+");
                    String uri = "http://www.omdbapi.com/?apikey=" + Account.APIKEY + "&t=" + name;
                    String responseString = getStringResponse(uri);

                    JSONObject movieResponse = new JSONObject(responseString);
                    JSONObject ratings = movieResponse.getJSONArray("Ratings").getJSONObject(0);

                    Movie movie = new Movie(movieResponse.getString("Title"),
                            movieResponse.getString("Plot"),
                            movieResponse.getString("Genre"),
                            movieResponse.getString("Director"),
                            ratings.getString("Value")
                    );
                    movieList.addMovie(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.weekly = movieList;
    }

    private String getStringResponse(String uri){
        String responseString = "";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        if (response != null) {
            responseString = response.body();
        }

        return responseString;
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
