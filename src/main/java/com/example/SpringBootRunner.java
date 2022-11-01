package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringBootRunner {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootRunner.class, args);
    System.out.println("go to the link http://localhost:8080/swagger-ui-custom.html.");
    System.out.println("go to the link for health http://localhost:8080/actuator/health");
  }
}
