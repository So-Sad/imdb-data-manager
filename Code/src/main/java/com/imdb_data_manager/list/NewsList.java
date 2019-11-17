package main.java.com.imdb_data_manager.list;

import main.java.com.imdb_data_manager.entity.News;

import java.util.ArrayList;
import java.util.List;

public class NewsList {

    private List<News> news = new ArrayList<>();

    public NewsList(List<News> news) {
        this.news = news;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

}
