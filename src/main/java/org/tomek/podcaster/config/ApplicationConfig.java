package org.tomek.podcaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tomek.podcaster.controller.Front;
import org.tomek.podcaster.tokfm.CategoryProvider;

@Configuration
public class ApplicationConfig {
    private final CategoryProvider categoryProvider;


    public ApplicationConfig(CategoryProvider categoryProvider) {
        this.categoryProvider = categoryProvider;
    }

    @Bean
    public Front front() {
        return new Front(categoryProvider);
    }
}
