package dev.tomek.podcaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {"org.tomek.podcaster"})
public class PodcasterApplication extends Application {
    private static String[] cmdArgs;
    private ConfigurableApplicationContext context;


    public static void main(String[] args) {
        cmdArgs = args;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        context = SpringApplication.run(PodcasterApplication.class, cmdArgs);
        primaryStage.setTitle("Podcaster FXML");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dev/tomek/podcaster/frontend/Podcaster.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        primaryStage.setScene(new Scene(fxmlLoader.load(), 1024, 600));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.stop();
    }
}
