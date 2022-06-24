package com.exchange.web.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;

/**
 * @author msaritas
 */
@Getter
public class ErrorResource extends AbstractMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty(value = "error")
    private final String error;

    public ErrorResource(String error) {
        super(MessageType.ERROR);
        this.error = error;
    }
}
