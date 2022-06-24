package com.exchange.web.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author msaritas
 */
@Getter
@Setter
public class ServiceResponseErrorResource implements Serializable {


    private static final long serialVersionUID = -7638994404716390272L;
    @JsonProperty(value = "code")
    private int code;
    @JsonProperty(value = "info")
    private String info;

}
