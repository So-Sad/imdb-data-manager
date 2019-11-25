module imdb.data.manager {
    requires org.json;
    requires org.jsoup;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires java.net.http;

    opens main.java.com.imdb_data_manager.application;
}