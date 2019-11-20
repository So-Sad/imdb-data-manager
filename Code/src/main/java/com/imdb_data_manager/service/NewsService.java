package main.java.com.imdb_data_manager.service;

import main.java.com.imdb_data_manager.list.NewsList;

public class NewsService {

    private NewsList news;

    public NewsList collectNews(){
        return news;
    }

    public NewsList getNews() {
        return news;
    }

    public void setNews(NewsList news) {
        this.news = news;
    }
}
