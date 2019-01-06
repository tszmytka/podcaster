package org.tomek.podcaster.frontend;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PodcasterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PodcasterController.class);
    private static final String PODCASTER_PROPERTIES = "podcaster.properties";


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
