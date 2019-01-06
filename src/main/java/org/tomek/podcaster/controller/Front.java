package org.tomek.podcaster.controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomek.podcaster.runner.PodcastPlayerRunner;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;
import org.tomek.podcaster.tokfm.PodcastUrlProvider;
import org.tomek.podcaster.tokfm.model.Category;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Front {
    private static final Logger LOGGER = LoggerFactory.getLogger(Front.class);
    private static final String PODCASTER_PROPERTIES = "podcaster.properties";
    private final CategoryProvider categoryProvider;
    private final PodcastProvider podcastProvider;
    private final PodcastUrlProvider podcastUrlProvider;
    private final PodcastPlayerRunner podcastPlayerRunner;

    private ListView<Category> lvCategories;
    private ListView<Podcast> lvPodcasts;
    private Button buttonPlay;


    public Front(CategoryProvider categoryProvider, PodcastProvider podcastProvider, PodcastUrlProvider podcastUrlProvider, PodcastPlayerRunner podcastPlayerRunner) {
        this.categoryProvider = categoryProvider;
        this.podcastProvider = podcastProvider;
        this.podcastUrlProvider = podcastUrlProvider;
        this.podcastPlayerRunner = podcastPlayerRunner;
    }


    public void render(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));
        borderPane.setTop(buildMenuBar());
        borderPane.setLeft(new HBox(getLvCategories()));
        borderPane.setCenter(new HBox(getLvPodcasts()));

        HBox bottomBox = new HBox(5, getButtonPlay());
        bottomBox.setAlignment(Pos.CENTER);
        BorderPane rightPane = new BorderPane();
        rightPane.setBottom(bottomBox);
        borderPane.setRight(rightPane);

        VBox info = new VBox(new Label("Podcast info goes here"));
        info.setAlignment(Pos.CENTER);
        info.setMinWidth(250);
        rightPane.setCenter(info);

        primaryStage.setTitle("Podcaster");
        primaryStage.setScene(new Scene(borderPane, 1024, 600));
        primaryStage.show();
    }

    private ListView<Category> getLvCategories() {
        if (lvCategories == null) {
            lvCategories = new ListView<>();
            lvCategories.getItems().addAll(categoryProvider.getCategories().values());
            lvCategories.setCellFactory(param -> new ListCell<Category>() {
                @Override
                protected void updateItem(Category category, boolean empty) {
                    super.updateItem(category, empty);
                    setText(empty ? null : category.getName());
                }
            });
            lvCategories.setMinWidth(300);
            lvCategories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            lvCategories.getSelectionModel().selectedItemProperty().addListener((observableValue, categoryOld, categoryNew) -> {
                try {
                    ListView<Podcast> lvPodcasts = getLvPodcasts();
                    lvPodcasts.setDisable(false);
                    getButtonPlay().setDisable(true);
                    lvPodcasts.getItems().setAll(podcastProvider.getPodcasts(new URL(categoryNew.getUrl())).values());
                } catch (MalformedURLException e) {
                    LOGGER.debug("Invalid url provided", e);
                }
            });
        }
        return lvCategories;
    }

    private ListView<Podcast> getLvPodcasts() {
        if (lvPodcasts == null) {
            lvPodcasts = new ListView<>();
            lvPodcasts.setDisable(true);
            lvPodcasts.setMinWidth(450);
            lvPodcasts.setCellFactory(param -> new ListCell<Podcast>() {
                @Override
                protected void updateItem(Podcast podcast, boolean empty) {
                    super.updateItem(podcast, empty);
                    setText(empty ? null : podcast.getTitle());
                }
            });
            lvPodcasts.setOnMouseClicked(event -> buttonPlay.setDisable(false));
        }
        return lvPodcasts;
    }

    private Button getButtonPlay() {
        if (buttonPlay == null) {
            buttonPlay = new Button("Play");
            buttonPlay.setMinWidth(100);
            buttonPlay.setDisable(true);
            buttonPlay.setDefaultButton(true);
            buttonPlay.setOnMouseClicked(event -> {
                buttonPlay.setText("Launching podcast ...");
                buttonPlay.setDisable(true);
                Podcast podcast = getLvPodcasts().getSelectionModel().getSelectedItem();
                podcastPlayerRunner.setPodcastPath(podcastUrlProvider.getPodcastUrl(String.valueOf(podcast.getId())));
                podcastPlayerRunner.run();

                TranslateTransition transition = new TranslateTransition(Duration.seconds(5), buttonPlay);
                transition.setOnFinished(actionEvent -> Platform.exit());
                transition.play();
            });
        }
        return buttonPlay;
    }

    /**
     * Whole method migrated to FXML
     */
    private MenuBar buildMenuBar() {
        Menu menuFile = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(actionEvent -> Platform.exit());
        menuFile.getItems().add(exit);

        Menu menuHelp = new Menu("Help");
        MenuItem about = new MenuItem("About");
        about.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            Properties properties = new Properties();
            try (InputStream resourceAsStream = getClass().getResourceAsStream("/" + PODCASTER_PROPERTIES)) {
                properties.load(resourceAsStream);
            } catch (IOException e) {
                LOGGER.warn("Could not read the properies file", e);
            }
            String version = properties.getProperty("application.version");
            alert.setHeaderText(null);
            alert.setContentText("Podcaster" + (version != null ? " ver: " + version : ""));
            alert.show();
        });

        menuHelp.getItems().add(about);
        return new MenuBar(menuFile, menuHelp);
    }
}
