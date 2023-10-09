package com.mavrictan.halloweengameapplication.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationConfiguration {
    @Value("${server.url.prefix}")
    private String URL_PREFIX;
}
