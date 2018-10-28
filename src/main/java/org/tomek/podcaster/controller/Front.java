package org.tomek.podcaster.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;
import org.tomek.podcaster.tokfm.model.Category;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.net.MalformedURLException;
import java.net.URL;

public class Front {
    private final CategoryProvider categoryProvider;
    private final PodcastProvider podcastProvider;

    private ListView<Category> lvCategories = new ListView<>();
    private ListView<Podcast> lvPodcasts = new ListView<>();
    private Button buttonPlay = new Button("Play");;


    public Front(CategoryProvider categoryProvider, PodcastProvider podcastProvider) {
        this.categoryProvider = categoryProvider;
        this.podcastProvider = podcastProvider;
    }


    public void render(Stage primaryStage) {
        Label label = new Label("Select from the categories and podcasts below.");

        lvCategories.getItems().addAll(categoryProvider.getCategories().values());
        lvCategories.setCellFactory(param -> new ListCell<Category>() {
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
                lvPodcasts.setDisable(false);
                buttonPlay.setDisable(true);
                lvPodcasts.getItems().setAll(podcastProvider.getPodcasts(new URL(category.getUrl())).values());
            } catch (MalformedURLException e) {
                //
            }
        });

        lvPodcasts.setDisable(true);
        lvPodcasts.setMinWidth(450);
        lvPodcasts.setCellFactory(param -> new ListCell<Podcast>() {
            @Override
            protected void updateItem(Podcast podcast, boolean empty) {
                super.updateItem(podcast, empty);
                setText(empty ? null : podcast.getTitle());
            }
        });
        lvPodcasts.setOnMouseClicked(event -> {
            buttonPlay.setDisable(false);
        });

        buttonPlay.setDisable(true);
        buttonPlay.setOnMouseClicked(event -> {
            Podcast podcast = lvPodcasts.getSelectionModel().getSelectedItem();

        });

        BorderPane borderPane = new BorderPane();
        HBox topBox = new HBox(20, label);
        topBox.setPadding(new Insets(5));
        borderPane.setTop(topBox);

        HBox leftBox = new HBox(lvCategories);
        borderPane.setLeft(leftBox);

        HBox centerBox = new HBox(lvPodcasts);
        borderPane.setCenter(centerBox);

        HBox bottomBox = new HBox(5, buttonPlay);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(bottomBox);

        primaryStage.setTitle("Podcaster");
        primaryStage.setScene(new Scene(borderPane, 800, 600));
        primaryStage.show();
    }
}
