package org.tomek.podcaster.controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.model.Category;

public class Front {
    private final CategoryProvider categoryProvider;


    public Front(CategoryProvider categoryProvider) {
        this.categoryProvider = categoryProvider;
    }


    public void render(Stage primaryStage) {
        Label label = new Label("Select from the categories and podcasts below.");
        ListView<Category> list1 = new ListView<>();
        list1.getItems().addAll(categoryProvider.getCategories().values());
        list1.setCellFactory(param -> new ListCell<Category>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                setText(empty ? null : category.getName());
            }
        });

        BorderPane borderPane = new BorderPane();
        HBox topBox = new HBox(20, label);
        topBox.setPadding(new Insets(5));
        borderPane.setTop(topBox);

        HBox leftBox = new HBox(list1);
        borderPane.setLeft(leftBox);

        primaryStage.setTitle("Podcaster");
        primaryStage.setScene(new Scene(borderPane, 640, 480));
        primaryStage.show();
    }


}
