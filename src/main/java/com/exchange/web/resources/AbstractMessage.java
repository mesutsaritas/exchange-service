package com.exchange.web.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author msaritas
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractMessage {

    @JsonProperty("type")
    private MessageType type;

}
