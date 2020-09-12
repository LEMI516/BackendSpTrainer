package com.sptrainer.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "jwttoken"
})
public class Token {
    @JsonProperty("jwttoken")
    private String jwttoken;
    @JsonIgnore
    private int status;
    @JsonIgnore
    private String msj;
    @JsonIgnore
    private String app;
    @JsonIgnore
    private Date date;

    public Token() {
    }

    public Token(String msj, int status, String app) {
        this.status = status;
        this.msj = msj;
        this.app = app;
    }
}
