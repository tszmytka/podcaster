package org.tomek.podcaster.controller;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;
import org.tomek.podcaster.tokfm.model.Category;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Front {
    private final CategoryProvider categoryProvider;
    private final PodcastProvider podcastProvider;

    private ListView<Category> lvCategories;
    private ListView<Podcast> lvPodcasts;
    private Button buttonPlay;


    public Front(CategoryProvider categoryProvider, PodcastProvider podcastProvider) {
        this.categoryProvider = categoryProvider;
        this.podcastProvider = podcastProvider;
    }


    public void render(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(buildMenuBar());
        borderPane.setLeft(new HBox(getLvCategories()));
        borderPane.setCenter(new HBox(getLvPodcasts()));

        HBox bottomBox = new HBox(5, getButtonPlay());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(bottomBox);

        primaryStage.setTitle("Podcaster");
        primaryStage.setScene(new Scene(borderPane, 800, 600));
        primaryStage.show();
    }

    private ListView<Category> getLvCategories() {
        if (lvCategories == null) {
            lvCategories = new ListView<>();
            lvCategories.getItems().addAll(categoryProvider.getCategories().values());
            lvCategories.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Category category, boolean empty) {
                    super.updateItem(category, empty);
                    setText(empty ? null : category.getName());
                }
            });
            lvCategories.setMinWidth(300);
            lvCategories.setOnMouseClicked(event -> {
                Category category = lvCategories.getSelectionModel().getSelectedItem();
                try {
                    ListView<Podcast> lvPodcasts = getLvPodcasts();
                    lvPodcasts.setDisable(false);
                    getButtonPlay().setDisable(true);
                    lvPodcasts.getItems().setAll(podcastProvider.getPodcasts(new URL(category.getUrl())).values());
                } catch (MalformedURLException e) {
                    //
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
            lvPodcasts.setCellFactory(param -> new ListCell<>() {
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
            buttonPlay.setDisable(true);
            buttonPlay.setOnMouseClicked(event -> {
                // todo play the selected podcast
//                Podcast podcast = getLvPodcasts().getSelectionModel().getSelectedItem();

            });
        }
        return buttonPlay;
    }

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
            try (InputStream resourceAsStream = getClass().getResourceAsStream("/podcaster.properties")) {
                properties.load(resourceAsStream);
            } catch (IOException e) {
                //
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
