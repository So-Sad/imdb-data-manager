package main.java.com.imdb_data_manager.service;

import main.java.com.imdb_data_manager.entity.News;
import main.java.com.imdb_data_manager.list.NewsList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsService {

    public void collectNews(NewsList newsList) {
        String newsPlot = "";
        String newsTitle = "";
        String newsDate = "";
        try {
            Document doc = Jsoup.connect("https://www.imdb.com/news/movie").get();
            Elements listNewsTitle = doc.select("h2.news-article__title");
            Elements listNewsPlot = doc.select("div.news-article__content");
            Elements listNewsDate = doc.select("li.ipl-inline-list__item.news-article__date");
            for (int i = 0; i < listNewsTitle.size(); i++) {
                News news = new News();

                Element elementTitle = listNewsTitle.get(i);
                newsTitle = elementTitle.text();
                news.setArticle(newsTitle);

                Element elementPlot = listNewsPlot.get(i);
                newsPlot = elementPlot.text();
                news.setDescription(newsPlot);

                Element elementDate = listNewsDate.get(i);
                newsDate = elementDate.text();
                news.setDate(newsDate);

                newsList.addNews(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
