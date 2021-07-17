package com.tiffin_umbrella.first_release_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FirstRelease1Application {

    public static void main(String[] args) {
        SpringApplication.run(FirstRelease1Application.class, args);
    }

}
