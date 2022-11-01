package com.example.webclientDemo.service;

import com.example.webclientDemo.dto.CustomerInfo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ServiceLayer {

  @Autowired private WebClient webClient;

  public static final String CIRCUITBREAKER_SERVICE = "myCircuitBreaker";
  public static final String RETRY_USER_SERVICE = "myRetry";

  @CircuitBreaker(name = CIRCUITBREAKER_SERVICE)
  @Retry(name = RETRY_USER_SERVICE, fallbackMethod = "getcustomerFallBack")
  public List<CustomerInfo> getCustomerDetails() {

    log.info("Calling Customer Service to Get Customer Data");

    String bearerToken = "aassdd";

    ArrayList customerInfo =
        this.webClient
            .post()
            .uri("/v1/{policyId}/riders", 1234)
            .headers(h -> h.set("bearer", bearerToken))
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ArrayList.class)
            .doOnError(
                err ->
                    log.error("Error Occoured while calling CorpSol, Retrying Again " + new Date()))
            .block();

    return customerInfo;
  }

  public List<CustomerInfo> getcustomerFallBack(Exception e) {
    // returned either a cached or hard-code value in fallback method
    return Stream.of(
            new CustomerInfo("DefaultCustomer1", 1),
            new CustomerInfo("DefaultCustomer2", 2),
            new CustomerInfo("DefaultCustomer3", 3),
            new CustomerInfo("DefaultCustomer4", 4),
            new CustomerInfo("DefaultCustomer4", 5))
        .collect(Collectors.toList());
  }
}
