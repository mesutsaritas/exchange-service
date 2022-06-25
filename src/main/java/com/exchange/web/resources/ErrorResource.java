package com.exchange.web.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author msaritas
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ErrorResource implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty(value = "errorCode")
  private int errorCode;

  @JsonProperty(value = "errorMessage")
  private String errorMessage;


}
