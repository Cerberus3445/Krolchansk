package ru.krolchansk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class KrolchanskApplication {

    public static void main(String[] args) {
        SpringApplication.run(KrolchanskApplication.class, args);
    }

}
