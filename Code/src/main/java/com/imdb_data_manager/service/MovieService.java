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

    public void collectTopRated(MovieList movieList) {
        String titleID;

        try {
            Document doc = Jsoup.connect("https://www.imdb.com/chart/top").get();
            Elements listTopRated = doc.select("div.wlb_ribbon");
            for (Element element : listTopRated) {
                titleID = element.attr("data-tconst");

                String uri = "http://www.omdbapi.com/?apikey=" + Account.APIKEY + "&i=" + titleID;
                String responseString = getStringResponse(uri);

                JSONObject movieResponse = new JSONObject(responseString);

                if (!movieResponse.getString("Response").equals("False")) {
                    Movie movie = new Movie(movieResponse.getString("Title"),
                            movieResponse.getString("Plot"),
                            movieResponse.getString("Genre"),
                            movieResponse.getString("Director"),
                            movieResponse.getString("imdbRating")
                    );
                    movieList.addMovie(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void collectComingSoon(MovieList movieList) {
        String title, name;

        try {
            Document doc = Jsoup.connect("https://www.imdb.com/movies-coming-soon").get();
            Elements listComingSoon = doc.select("h4 > a");
            for (Element element : listComingSoon) {
                title = element.attr("title");
                if (!title.isEmpty()) {
                    name = title.substring(0, title.length() - 7);

                    name = name.toLowerCase().replaceAll(" ", "+");
                    String uri = "http://www.omdbapi.com/?apikey=" + Account.APIKEY + "&t=" + name;
                    String responseString = getStringResponse(uri);

                    JSONObject movieResponse = new JSONObject(responseString);

                    if (!movieResponse.getString("Response").equals("False")) {
                        Movie movie = new Movie(movieResponse.getString("Title"),
                                movieResponse.getString("Plot"),
                                movieResponse.getString("Genre"),
                                movieResponse.getString("Director"),
                                movieResponse.getString("imdbRating")
                        );
                        movieList.addMovie(movie);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void collectWeekly(MovieList movieList) {
        String title, name;

        try {
            Document doc = Jsoup.connect("https://www.imdb.com/movies-in-theaters").get();
            Elements listWeekly = doc.select("h4 > a");
            for (Element element : listWeekly) {
                title = element.attr("title");
                if (!title.isEmpty()) {
                    name = title.substring(0, title.length() - 7);

                    name = name.toLowerCase().replaceAll(" ", "+");
                    String uri = "http://www.omdbapi.com/?apikey=" + Account.APIKEY + "&t=" + name;
                    String responseString = getStringResponse(uri);

                    JSONObject movieResponse = new JSONObject(responseString);

                    if (!movieResponse.getString("Response").equals("False")) {
                        Movie movie = new Movie(movieResponse.getString("Title"),
                                movieResponse.getString("Plot"),
                                movieResponse.getString("Genre"),
                                movieResponse.getString("Director"),
                                movieResponse.getString("imdbRating")
                        );
                        movieList.addMovie(movie);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStringResponse(String uri) {
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

}
