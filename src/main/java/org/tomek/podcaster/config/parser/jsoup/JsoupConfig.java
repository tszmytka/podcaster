package org.tomek.podcaster.config.parser.jsoup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tomek.podcaster.parser.jsoup.JsoupConnector;

@Configuration
public class JsoupConfig {
    @Bean
    public JsoupConnector jsoupConnector() {
        return new JsoupConnector();
    }
}
