package main.java.com.imdb_data_manager.entity;

public class Movie {

    private String name;
    private String description;
    private String genre;
    private String director;
    private String rating;

    public Movie(String name, String description, String genre, String director, String rating) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.director = director;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
