package org.tomek.podcaster.frontend;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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
import java.util.ResourceBundle;

@Component
public class PodcasterController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PodcasterController.class);
    // todo "podcaster.properties" is generated automatically - the file is not available if mvn install hasn't been run
    private static final String PODCASTER_PROPERTIES = "podcaster.properties";

    private final CategoryProvider categoryProvider;
    private final PodcastProvider podcastProvider;
    private final PodcastUrlProvider podcastUrlProvider;
    private final PodcastPlayerRunner podcastPlayerRunner;

    @FXML
    private ListView<Category> lvCategories;

    private ListView<Podcast> lvPodcasts;

    private Button buttonPlay;

    public PodcasterController(CategoryProvider categoryProvider, PodcastProvider podcastProvider, PodcastUrlProvider podcastUrlProvider, PodcastPlayerRunner podcastPlayerRunner) {
        this.categoryProvider = categoryProvider;
        this.podcastProvider = podcastProvider;
        this.podcastUrlProvider = podcastUrlProvider;
        this.podcastPlayerRunner = podcastPlayerRunner;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvCategories.getItems().addAll(categoryProvider.getCategories().values());
        lvCategories.setCellFactory(param -> new ListCell<Category>() {
            @Override
            protected void updateItem(Category category, boolean empty) {
                super.updateItem(category, empty);
                setText(empty ? null : category.getName());
            }
        });
        MultipleSelectionModel<Category> selectionModel = lvCategories.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        selectionModel.selectedItemProperty().addListener((observableValue, categoryOld, categoryNew) -> {
            try {
                lvPodcasts.setDisable(false);
                buttonPlay.setDisable(true);
                lvPodcasts.getItems().setAll(podcastProvider.getPodcasts(new URL(categoryNew.getUrl())).values());
            } catch (MalformedURLException e) {
                LOGGER.debug("Invalid url provided", e);
            }
        });
    }

    public void menuFileExit() {
        Platform.exit();
    }

    public void menuHelpAbout() {
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
    }
}
