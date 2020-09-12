package com.sptrainer.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "username",
        "password"
})
public class AutheticationToken {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public AutheticationToken() {
    }

    public AutheticationToken(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
