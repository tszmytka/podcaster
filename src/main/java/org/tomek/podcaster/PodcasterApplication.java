package org.tomek.podcaster;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.tomek.podcaster.controller.Front;

@SpringBootApplication(scanBasePackages = {"org.tomek.podcaster"})
public class PodcasterApplication extends Application {
    private static String[] cmdArgs;


    public static void main(String[] args) {
        cmdArgs = args;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ConfigurableApplicationContext context = SpringApplication.run(PodcasterApplication.class, cmdArgs);
        context.getBean(Front.class).render(primaryStage);
    }
}
