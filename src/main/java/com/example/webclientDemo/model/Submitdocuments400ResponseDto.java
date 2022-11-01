package com.example.webclientDemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

/**
 * Submitdocuments400ResponseDto
 */

@JsonTypeName("submitdocuments_400_response")

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Submitdocuments400ResponseDto {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("details")
  private String details;
  
  
  
}
