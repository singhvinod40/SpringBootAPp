package com.example.config;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

@Configuration
public class BeanConfig {


  @Autowired
  Environment env;


  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl(
            "http://localhost:8085") // in real time we will give service name hiding behind the
                                     // service discovery
        .build();
  }

  @Bean
  public CloudBlobClient cloudBlobClient() throws URISyntaxException, StorageException, InvalidKeyException {
    CloudStorageAccount storageAccount = CloudStorageAccount.parse(env.getProperty("storage.storageAccount"));
    return storageAccount.createCloudBlobClient();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return NoOpPasswordEncoder.getInstance();
  }
}
