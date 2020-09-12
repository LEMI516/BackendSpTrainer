package com.sptrainer.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "iduser",
        "name",
        "description",
        "quantity",
        "active",
        "state",
        "enddate",
        "startdate",
        "type_schedule",
        "address",
        "sitie",
        "coordinate",
        "sessions",
        "members",
        "colour",
        "category",
        "distance",
        "user",
        "calificationuser",
        "date_save",
        "date_update",
        "date_publish",
        "state_user_consulting",
        "requests",
        "quantity_solicitude_pending",
        "quantity_member"
})
public class GroupTrainer implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("iduser")
    private Long iduser;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("active")
    private String active;
    @JsonProperty("state")
    private String state;
    @JsonProperty("enddate")
    private String enddate;
    @JsonProperty("startdate")
    private String startdate;
    @JsonProperty("type_schedule")
    private String type_schedule;
    @JsonProperty("address")
    private String address;
    @JsonProperty("sitie")
    private String sitie;
    @JsonProperty("coordinate")
    private String coordinate;
    @JsonProperty("colour")
    private String colour;
    @JsonProperty("category")
    private String category;
    @JsonProperty("sessions")
    private List<SesionTrainer> sessions = new ArrayList<>();
    @JsonProperty("members")
    private List<Member> members = new ArrayList<>();
    @JsonProperty("requests")
    private List<RegistrationRequest> requests = new ArrayList<>();
    @JsonProperty("distance")
    private double distance;
    @JsonProperty("user")
    private User user;
    @JsonProperty("calificationuser")
    private Calification calificationuser;
    @JsonProperty("date_save")
    private String date_save;
    @JsonProperty("date_update")
    private String date_update;
    @JsonProperty("date_publish")
    private String date_publish;
    @JsonProperty("state_user_consulting")
    private String state_user_consulting;
    @JsonProperty("quantity_solicitude_pending")
    private int quantity_solicitude_pending;
    @JsonProperty("quantity_member")
    private int quantity_member;


    public GroupTrainer() {

    }

    public GroupTrainer(Long id, Long iduser, String name, String description, int quantity, String active, String state, String enddate, String startdate, String type_schedule, String address, String sitie, String coordinate, String colour) {
        this.id = id;
        this.iduser = iduser;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.active = active;
        this.state = state;
        this.enddate = enddate;
        this.startdate = startdate;
        this.type_schedule = type_schedule;
        this.address = address;
        this.sitie = sitie;
        this.coordinate = coordinate;
        this.colour = colour;
    }
}
