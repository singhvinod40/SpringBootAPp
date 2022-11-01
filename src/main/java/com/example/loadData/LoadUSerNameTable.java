package com.example.loadData;

import com.example.AuthController.entity.User;
import com.example.AuthController.repositary.UserRepository;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoadUSerNameTable {

  @Autowired private UserRepository userRepository;

  @PostConstruct
  public void initializeUserTable() {

    log.info("Adding PostConstructData");
    List<User> userList =
        Stream.of(
                new User(101, "user", "password", "user@gmail.com"),
                new User(102, "javaAdmin", "Admin", "javaAdmin@gmail.com"),
                new User(103, "javauser", "userpassword", "javauser@gmail.com"))
            .collect(Collectors.toList());

    log.info("Adding userd data to table");
    userRepository.saveAll(userList);
  }
}
