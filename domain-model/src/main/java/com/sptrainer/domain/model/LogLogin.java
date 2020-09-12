package com.sptrainer.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "username",
        "dateLogin",
        "ipAddress",
        "result"
})
public class LogLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("dateLogin")
    private String dateLogin;
    @JsonProperty("ipAddress")
    private String ipAddress;
    @JsonProperty("result")
    private String result;
    @JsonProperty("version")
    private String version;

}
