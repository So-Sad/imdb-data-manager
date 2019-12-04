package main.java.com.imdb_data_manager.service;

import main.java.com.imdb_data_manager.entity.Account;
import main.java.com.imdb_data_manager.entity.Movie;
import main.java.com.imdb_data_manager.list.Rating;
import main.java.com.imdb_data_manager.list.Watchlist;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("Duplicates")
public class AccountService {

    public void collectRating(Account account) {
        String ratingsTitle = "", personalRating = "";

        try {
            Document doc = Jsoup.connect("https://www.imdb.com/user/ur108105157/ratings").get();
            Elements listRatings = doc.select("a > img");
            Elements listPersonalRatings = doc.select("div.ipl-rating-star.ipl-rating-star--other-user.small > span.ipl-rating-star__rating");
            for (int i = 0; i < listRatings.size(); i++) {
                Element elementTitle = listRatings.get(i);
                Element elementRating = listPersonalRatings.get(i);
                ratingsTitle = elementTitle.attr("alt");
                personalRating = elementRating.text();

                ratingsTitle = ratingsTitle.toLowerCase().replaceAll(" ", "+");
                String uri = "http://www.omdbapi.com/?apikey=" + Account.APIKEY + "&t=" + ratingsTitle;
                String responseString = MovieService.getStringResponse(uri);

                JSONObject movieResponse = new JSONObject(responseString);

                if (!movieResponse.getString("Response").equals("False")) {
                    Movie movie = new Movie(movieResponse.getString("Title"),
                            movieResponse.getString("Plot"),
                            movieResponse.getString("Genre"),
                            movieResponse.getString("Director"),
                            personalRating
                    );
                    account.getRating().addMovie(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void collectWatchList(Account account) {
        String watchlistTitle = "";
        String docString = "";
        Pattern pattern = Pattern.compile("\"title\":\".+?\"},\"episode\"");
        Matcher matcher;
        try {
            Document doc = Jsoup.connect("https://www.imdb.com/user/" + account.getLogin() + "/watchlist").get();
            docString = doc.toString();
            matcher = pattern.matcher(docString);
            while (matcher.find()) {
                watchlistTitle = docString.substring(matcher.start(), matcher.end());
                watchlistTitle = watchlistTitle.substring(9, watchlistTitle.length() - 12);

                watchlistTitle = watchlistTitle.toLowerCase().replaceAll(" ", "+");
                String uri = "http://www.omdbapi.com/?apikey=" + Account.APIKEY + "&t=" + watchlistTitle;
                String responseString = MovieService.getStringResponse(uri);

                JSONObject movieResponse = new JSONObject(responseString);

                if (!movieResponse.getString("Response").equals("False")) {
                    Movie movie = new Movie(movieResponse.getString("Title"),
                            movieResponse.getString("Plot"),
                            movieResponse.getString("Genre"),
                            movieResponse.getString("Director"),
                            movieResponse.getString("imdbRating")
                    );
                    account.getWatchlist().addMovie(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void collectNickName(Account account) {
        String nickName;

        try {
            Document doc = Jsoup.connect("https://www.imdb.com/user/" + account.getLogin()).get();
            Elements listRecommended = doc.select("h1");
            for (Element element : listRecommended) {
                nickName = element.text();
                account.setNickName(nickName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkLogin(String login) {
        if (Pattern.matches("ur\\d{9}", login)
                || Pattern.matches("ur\\d{8}", login)
                || Pattern.matches("ur\\d{7}", login)) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.imdb.com/user/" + login))
                    .build();
            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null) {
                return response.statusCode() == 200;
            } else return false;
        } else return false;
    }

    public static boolean checkPrivate(Account account) throws IOException {
        Document doc = Jsoup.connect("https://www.imdb.com/user/" + account.getLogin()).get();
        Elements list = doc.select("div.subNavItem.inactive");
        return list.isEmpty();
    }

}
