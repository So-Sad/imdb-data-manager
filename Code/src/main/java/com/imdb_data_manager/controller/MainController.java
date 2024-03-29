package main.java.com.imdb_data_manager.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.com.imdb_data_manager.entity.Account;
import main.java.com.imdb_data_manager.entity.Movie;
import main.java.com.imdb_data_manager.entity.News;
import main.java.com.imdb_data_manager.list.MovieList;
import main.java.com.imdb_data_manager.list.NewsList;
import main.java.com.imdb_data_manager.service.AccountService;
import main.java.com.imdb_data_manager.service.MovieService;
import main.java.com.imdb_data_manager.service.NewsService;

import java.io.IOException;

@SuppressWarnings({"unchecked", "Duplicates"})
public class MainController {

    @FXML
    private ComboBox moviesPicker;

    @FXML
    private ComboBox accountPicker;

    @FXML
    private Button newsBtn;

    @FXML
    private Button refreshAccountBtn;

    @FXML
    private Label accountLabel;

    @FXML
    private TableView movieTable;

    @FXML
    private TableColumn<Movie, String> movieTitleClmn;

    @FXML
    private TableColumn<Movie, String> movieDescriptionClmn;

    @FXML
    private TableColumn<Movie, String> movieGenresClmn;

    @FXML
    private TableColumn<Movie, String> movieDirectorClmn;

    @FXML
    private TableColumn<Movie, String> movieRatingClmn;

    @FXML
    private TableColumn<News, String> newsArticleClmn = new TableColumn<News, String>("Article");

    @FXML
    private TableColumn<News, String> newsContentClmn = new TableColumn<News, String>("Content");

    @FXML
    private TableColumn<News, String> newsDateClmn = new TableColumn<News, String>("Date");

    private Account account = new Account();
    private boolean ratingsFlag = false;
    private boolean watchlistFlag = false;
    private boolean topRatedFlag = false;
    private boolean weeklyFlag = false;
    private boolean comingSoonFlag = false;
    private boolean newsFlag = false;
    private MovieList topRatedList = new MovieList();
    private MovieList weeklyList = new MovieList();
    private MovieList comingSoonList = new MovieList();
    private NewsList newsList = new NewsList();
    private MovieService movieService = new MovieService();
    private NewsService newsService = new NewsService();
    private AccountService accountService = new AccountService();

    private ObservableList<String> moviesPickerList = FXCollections.observableArrayList("Top Rated", "Weekly", "Coming Soon");
    private ObservableList<String> accountPickerList = FXCollections.observableArrayList("Ratings", "Watchlist");

    @FXML
    public void initialize() {
        initAccount();
        initMoviesPicker();
        setNewsBtn();
    }

    private void initAccount() {
        account.setLogin(this.getAuthLogin());
        if (account.getLogin() == null) {
            accountLabel.setText("Sing In");
            accountPicker.setDisable(true);
            refreshAccountBtn.setDisable(true);
        } else {
            setAccount();
        }
    }

    private String getAuthLogin() {
        return AuthorizationController.authLogin.equals("guest") ? null : AuthorizationController.authLogin;
    }

    private void setAccount() {
        AccountService.collectNickName(account);
        try {
            if (AccountService.checkPrivate(account)) {
                accountLabel.setText(account.getNickName() + " (private)");
                accountPicker.setDisable(true);
                refreshAccountBtn.setDisable(true);
            } else {
                accountLabel.setText(account.getNickName());
                initAccountPicker();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setRefreshAccountBtn() {
        setAccount();
    }

    private void initAccountPicker() {
        accountPicker.setItems(accountPickerList);
        accountPicker.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals(oldValue)) {
                    return;
                }
                newsBtn.setDisable(false);
                moviesPicker.getSelectionModel().clearSelection();
                setPickerCell(moviesPicker, "Movie");

                if (newValue.equals(accountPickerList.get(0))) {             //ratings
                    if (!ratingsFlag) {
                        accountService.collectRating(account);
                    }
                    ObservableList<Movie> movieObservableList = FXCollections.observableList(account.getRating().getMovies());
                    movieTable.setItems(movieObservableList);
                    movieTable.getColumns().setAll(movieTitleClmn, movieDescriptionClmn, movieGenresClmn, movieDirectorClmn, movieRatingClmn);
                    ratingsFlag = true;
                } else if (newValue.equals(accountPickerList.get(1))) {      //watchlist
                    if(!watchlistFlag) {
                        accountService.collectWatchList(account);
                    }
                    ObservableList<Movie> movieObservableList = FXCollections.observableList(account.getWatchlist().getMovies());
                    movieTable.setItems(movieObservableList);
                    movieTable.getColumns().setAll(movieTitleClmn, movieDescriptionClmn, movieGenresClmn, movieDirectorClmn, movieRatingClmn);
                    watchlistFlag = true;
                }
            }
        });
    }

    private void initMoviesPicker() {
        moviesPicker.setItems(moviesPickerList);
        setMovieTable();

        moviesPicker.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals(oldValue)) {
                    return;
                }
                newsBtn.setDisable(false);
                accountPicker.getSelectionModel().clearSelection();
                setPickerCell(accountPicker, "Account");

                if (newValue.equals(moviesPickerList.get(0))) {             //top rated
                    if (!topRatedFlag) {
                        movieService.collectTopRated(topRatedList);
                    }
                    ObservableList<Movie> movieObservableList = FXCollections.observableList(topRatedList.getMovies());
                    movieTable.setItems(movieObservableList);
                    movieTable.getColumns().setAll(movieTitleClmn, movieDescriptionClmn, movieGenresClmn, movieDirectorClmn, movieRatingClmn);
                    topRatedFlag = true;
                } else if (newValue.equals(moviesPickerList.get(1))) {       //weekly
                    if(!weeklyFlag) {
                        movieService.collectWeekly(weeklyList);
                    }
                    ObservableList<Movie> movieObservableList = FXCollections.observableList(weeklyList.getMovies());
                    movieTable.setItems(movieObservableList);
                    movieTable.getColumns().setAll(movieTitleClmn, movieDescriptionClmn, movieGenresClmn, movieDirectorClmn, movieRatingClmn);
                    weeklyFlag = true;
                } else if (newValue.equals(moviesPickerList.get(2))) {       //coming soon
                    if (!comingSoonFlag) {
                        movieService.collectComingSoon(comingSoonList);
                    }
                    ObservableList<Movie> movieObservableList = FXCollections.observableList(comingSoonList.getMovies());
                    movieTable.setItems(movieObservableList);
                    movieTable.getColumns().setAll(movieTitleClmn, movieDescriptionClmn, movieGenresClmn, movieDirectorClmn, movieRatingClmn);
                    comingSoonFlag = true;
                }
            }
        });
    }

    private void setMovieTable() {
        movieTitleClmn.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        movieDescriptionClmn.setCellValueFactory(new PropertyValueFactory<Movie, String>("description"));
        movieGenresClmn.setCellValueFactory(new PropertyValueFactory<Movie, String>("genre"));
        movieDirectorClmn.setCellValueFactory(new PropertyValueFactory<Movie, String>("director"));
        movieRatingClmn.setCellValueFactory(new PropertyValueFactory<Movie, String>("rating"));
        setMovieColumnCellProperties(movieTitleClmn);
        setMovieColumnCellProperties(movieDescriptionClmn);
        setMovieColumnCellProperties(movieGenresClmn);
        setMovieColumnCellProperties(movieDirectorClmn);
        setMovieColumnCellProperties(movieRatingClmn);
    }

    @FXML
    private void setNewsBtn() {
        if (!newsFlag) {
            newsService.collectNews(newsList);
        }

        movieTable.getColumns().clear();
        ObservableList<News> newsObservableList = FXCollections.observableList(newsList.getNewsList());
        movieTable.setItems(newsObservableList);

        setNewsTable();
        movieTable.getColumns().addAll(newsArticleClmn, newsContentClmn, newsDateClmn);
        newsBtn.setDisable(true);
        newsFlag = true;
    }

    private void setNewsTable() {
        newsArticleClmn.setCellValueFactory(new PropertyValueFactory<News, String>("article"));
        newsContentClmn.setCellValueFactory(new PropertyValueFactory<News, String>("description"));
        newsDateClmn.setCellValueFactory(new PropertyValueFactory<News, String>("date"));

        newsArticleClmn.setPrefWidth(250);
        setNewsColumnCellProperties(newsArticleClmn);
        newsArticleClmn.setResizable(false);
        newsContentClmn.setPrefWidth(750);
        setNewsColumnCellProperties(newsContentClmn);
        newsContentClmn.setResizable(false);
        newsDateClmn.setPrefWidth(200);
        setNewsColumnCellProperties(newsDateClmn);
        newsContentClmn.setResizable(false);

        accountPicker.getSelectionModel().clearSelection();
        setPickerCell(accountPicker, "Account");
        moviesPicker.getSelectionModel().clearSelection();
        setPickerCell(moviesPicker, "Movie");
    }

    private void setPickerCell(ComboBox comboBox, String promptText) {
        comboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(promptText);
                } else {
                    setText(item);
                }
            }
        });
    }

    private void setNewsColumnCellProperties(TableColumn tableColumn) {
        tableColumn.setCellFactory(tc -> {
            TableCell<News, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(tableColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            text.setFill(Color.WHITESMOKE);
            return cell;
        });
    }

    private void setMovieColumnCellProperties(TableColumn tableColumn) {
        tableColumn.setCellFactory(tc -> {
            TableCell<Movie, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(tableColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            text.setFill(Color.WHITESMOKE);
            return cell;
        });
    }
}
