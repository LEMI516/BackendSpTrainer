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
        "document",
        "firstname",
        "lastname",
        "birthdate",
        "phone",
        "password",
        "user",
        "rolid",
        "email",
        "weight",
        "height",
        "direction",
        "city",
        "department",
        "profile",
        "sex",
        "photo",
        "coordinate",
        "quantitygroup",
        "date_save",
        "date_update",
        "calification"
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("document")
    private String document;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("birthdate")
    private String birthdate;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("password")
    private String password;
    @JsonProperty("user")
    private String user;
    @JsonProperty("rolid")
    private String rolid;
    @JsonProperty("email")
    private String email;
    @JsonProperty("weight")
    private Double weight;
    @JsonProperty("height")
    private Double height;
    @JsonProperty("direction")
    private String direction;
    @JsonProperty("city")
    private String city;
    @JsonProperty("department")
    private String department;
    @JsonProperty("profile")
    private String profile;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("coordinate")
    private String coordinate;
    @JsonProperty("quantitygroup")
    private int quantitygroup;
    @JsonProperty("date_save")
    private String date_save;
    @JsonProperty("date_update")
    private String date_update;
    @JsonProperty("calification")
    private Calification calification = new Calification();

    public User() {
    }

    public User(Long id, String document, String firstname, String lastname, String birthdate, String phone, String password, String user, String rolid, String email, Double weight, Double height, String direction, String city, String department, String profile, String sex, String photo
            , String coordinate) {
        this.id = id;
        this.document = document;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.phone = phone;
        this.password = password;
        this.user = user;
        this.rolid = rolid;
        this.email = email;
        this.weight = weight;
        this.height = height;
        this.direction = direction;
        this.city = city;
        this.department = department;
        this.profile = profile;
        this.sex = sex;
        this.photo = photo;
        this.coordinate = coordinate;
    }
}
