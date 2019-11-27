package main.java.com.imdb_data_manager.service;

import main.java.com.imdb_data_manager.entity.News;
import main.java.com.imdb_data_manager.list.NewsList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsService {

    public void collectNews(NewsList newsList){
        String newsPlot = "";
        String newsTitle = "";
        News news = new News();

        try {
            Document doc = Jsoup.connect("https://www.imdb.com/news/movie").get();
            Elements listNewsTitle = doc.select("h2.news-article__title");
            for (Element element : listNewsTitle) {
                newsTitle = element.text();
                news.setArticle(newsTitle);
            }
            Elements listNewsPlot = doc.select("div.news-article__content");
            for (Element element : listNewsPlot){
                newsPlot = element.text();
                news.setDescription(newsPlot);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        newsList.addNews(news);
    }

}
