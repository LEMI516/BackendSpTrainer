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
        "idgrup",
        "iduser",
        "colour",
        "description",
        "type_notification",
        "id_registration_request",
        "idsesion",
        "datenotificacion",
        "state",
        "idusergenerate",
        "iduserread"
})
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("idgrup")
    private Long idgrup;
    @JsonProperty("iduser")
    private Long iduser;
    @JsonProperty("colour")
    private String colour;
    @JsonProperty("description")
    private String description;
    @JsonProperty("type_notification")
    private String type_notification;
    @JsonProperty("id_registration_request")
    private Long id_registration_request;
    @JsonProperty("idsesion")
    private Long idsesion;
    @JsonProperty("state")
    private String state;
    @JsonProperty("datenotificacion")
    private String datenotificacion;
    @JsonProperty("idusergenerate")
    private Long idusergenerate;
    @JsonProperty("iduserread")
    private Long iduserread;


    public Notification() {
    }

    public Notification(Long idgrup, Long iduser, String description, String type_notification, Long id_registration_request, Long idsesion, String state,Long idusergenerate) {
        this.idgrup = idgrup;
        this.iduser = iduser;
        this.description = description;
        this.type_notification = type_notification;
        this.id_registration_request = id_registration_request;
        this.idsesion = idsesion;
        this.state = state;
        this.idusergenerate = idusergenerate;
    }
}
