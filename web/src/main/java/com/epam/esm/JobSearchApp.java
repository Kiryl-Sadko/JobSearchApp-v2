package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = JacksonAutoConfiguration.class)
public class JobSearchApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(com.epam.esm.JobSearchApp.class, args);
    }
}
