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
        "idnotification",
        "state",
        "dateread",
        "iduser"
})
public class NotificationRead implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("idnotification")
    private Long idnotification;
    @JsonProperty("state")
    private String state;
    @JsonProperty("dateread")
    private String dateread;
    @JsonProperty("iduser")
    private Long iduser;

    public NotificationRead(){}
}
