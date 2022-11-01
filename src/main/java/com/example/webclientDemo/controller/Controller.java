package com.example.webclientDemo.controller;

import com.example.webclientDemo.dto.CustomerInfo;
import com.example.webclientDemo.service.ServiceLayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.ArrayList;
import java.util.List;

import static com.example.utill.Constant.*;

@RestController
@Slf4j
@RequestMapping("/test")
public class Controller {

  @Autowired private ServiceLayer serviceLayer;

  @GetMapping("")
  public ResponseEntity<String> checkStatus() {
    return ResponseEntity.ok().body("Server is up and Running");
  }

  @GetMapping("/getCustomerInfo")
  public ResponseEntity<List<CustomerInfo>> getCustomerInfo() {

    List<CustomerInfo> customerInfo = new ArrayList<>();
    try {
      log.info("GetCustomer info " + CONTROLLER + START);

      return ResponseEntity.ok().body(serviceLayer.getCustomerDetails());

    } catch (WebClientRequestException e) {
      log.info("Bussiness Exception" + CONTROLLER + END);
      return ResponseEntity.internalServerError().body(new ArrayList<>());
    } catch (Exception e) {
      log.error("Exception Occoured" + CONTROLLER + END);
      e.printStackTrace();
      return ResponseEntity.badRequest().body(new ArrayList<>());
    }
  }
}
