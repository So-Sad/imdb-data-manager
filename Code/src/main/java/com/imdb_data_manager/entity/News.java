package main.java.com.imdb_data_manager.entity;

public class News {

    private String article;
    private String description;
    private String date;

    public News(String article, String description, String date) {
        this.article = article;
        this.description = description;
        this.date = date;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
