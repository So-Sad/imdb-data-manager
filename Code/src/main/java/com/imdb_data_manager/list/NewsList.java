package main.java.com.imdb_data_manager.list;

import main.java.com.imdb_data_manager.entity.News;

import java.util.ArrayList;
import java.util.List;

public class NewsList {

    private List<News> newsList = new ArrayList<>();

    public NewsList() {
    }

    public NewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public boolean addNews(News news){
        if(newsList.contains(news)){
            return false;
        }
        else {
            newsList.add(news);
            return true;
        }
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

}
