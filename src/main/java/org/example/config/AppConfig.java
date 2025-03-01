package org.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@ConstructorBinding
@ConfigurationProperties(prefix = "src")
public class AppConfig {
    private String path;

    public AppConfig(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
