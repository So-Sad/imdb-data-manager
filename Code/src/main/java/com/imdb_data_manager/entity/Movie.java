package main.java.com.imdb_data_manager.entity;

public class Movie {

    private String name;
    private String description;
    private String genre;
    private String director;

    public Movie(String name, String description, String genre, String director) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.director = director;
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
}
