package org.tomek.podcaster;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;
import org.tomek.podcaster.tokfm.model.Category;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"org.tomek.podcaster"})
public class PodcasterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PodcasterApplication.class, args);
    }


//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//        };
//    }


//    private static void getPodcasts() throws MalformedURLException {
//        CategoryProvider categoryProvider = new CategoryProvider(new URL("https://audycje.tokfm.pl/audycje-tokfm"));
////        Map<Integer, Category> categories = categoryProvider.getCategories();
//        Category category = categoryProvider.getCategory(20);
//        PodcastProvider podcastProvider = new PodcastProvider(new URL(category.getUrl()));
//        Map<Integer, Podcast> podcasts = podcastProvider.getPodcasts();
//        System.out.println(podcasts);
//    }
}
