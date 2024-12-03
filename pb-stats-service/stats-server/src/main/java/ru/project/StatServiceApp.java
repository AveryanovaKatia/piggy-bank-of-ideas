package ru.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication

public class StatServiceApp {   public static void main(String[] args) {
    SpringApplication.run(StatServiceApp.class, args);

    log.info("Приложение StatServiceApp запущено");
}

}