package org.tomek.podcaster;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.tomek.podcaster.controller.Front;
import org.tomek.podcaster.tokfm.CategoryProvider;

@SpringBootApplication(scanBasePackages = {"org.tomek.podcaster"})
public class PodcasterApplication extends Application {
    private static String[] cmdArgs;

    public static void main(String[] args) {
//        SpringApplication.run(PodcasterApplication.class, args);
        cmdArgs = args;
        launch(args);
    }


//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        //noinspection Convert2MethodRef - javafx.application.Application checks the calling calss name out of the stack trace and causes problems with method reference here
//        return args -> launch(args);
//    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(PodcasterApplication.class, cmdArgs);
        context.getBean(Front.class).render(primaryStage);

//        Label label = new Label("Select from the categories and podcasts below.");
//        ListView<String> list1 = new ListView<>();
//        list1.getItems().addAll("First", "Second");
//
//        BorderPane borderPane = new BorderPane();
//        HBox topBox = new HBox(20, label);
//        topBox.setPadding(new Insets(5));
//        borderPane.setTop(topBox);
//
//        HBox leftBox = new HBox(list1);
//        borderPane.setLeft(leftBox);
//
//        primaryStage.setTitle("Podcaster");
//        primaryStage.setScene(new Scene(borderPane, 640, 480));
//        primaryStage.show();
    }


//    private static void getPodcasts() throws MalformedURLException {
//        CategoryProvider categoryProvider = new CategoryProvider(new URL("https://audycje.tokfm.pl/audycje-tokfm"));
////        Map<Integer, Category> categories = categoryProvider.getCategories();
//        Category category = categoryProvider.getCategory(20);
//        PodcastProvider podcastProvider = new PodcastProvider(new URL(category.getUrl()));
//        Map<Integer, Podcast> podcasts = podcastProvider.getPodcasts();
//        System.out.println(podcasts);
//    }
}
