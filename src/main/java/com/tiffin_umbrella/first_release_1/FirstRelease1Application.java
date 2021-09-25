package com.tiffin_umbrella.first_release_1;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.FileHandler;

@EnableAsync
@SpringBootApplication
public class FirstRelease1Application {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = FirstRelease1Application.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader
                .getResource("serviceAccountKey.json")).getFile());
        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

//        FileInputStream serviceAccount =
//                new FileInputStream("path/to/serviceAccountKey.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("fir-db-for-spring-boot-30cac")
                .build();
        FirebaseApp.initializeApp(options);

        SpringApplication.run(FirstRelease1Application.class, args);
    }

}
