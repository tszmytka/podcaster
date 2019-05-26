package dev.tomek.podcaster.config.parser.jsoup;

import dev.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsoupConfig {
    @Bean
    public JsoupConnector jsoupConnector() {
        return new JsoupConnector();
    }
}
