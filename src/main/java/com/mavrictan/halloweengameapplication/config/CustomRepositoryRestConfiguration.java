package com.mavrictan.halloweengameapplication.config;

import com.mavrictan.halloweengameapplication.entity.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CustomRepositoryRestConfiguration {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Player.class);
        });
    }
}