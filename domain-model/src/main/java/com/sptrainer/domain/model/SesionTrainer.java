package com.sptrainer.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
        "idgroup",
        "iduser",
        "startday",
        "endday",
        "starthour",
        "endhour",
        "active",
        "address",
        "description",
        "coordinate",
        "name",
        "sitiedefault",
        "distance",
        "date_save",
        "date_update"
})
public class SesionTrainer implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("idgroup")
    private Long idgroup;
    @JsonProperty("iduser")
    private Long iduser;
    @JsonProperty("startday")
    private String startday;
    @JsonProperty("endday")
    private String endday;
    @JsonProperty("starthour")
    private String starthour;
    @JsonProperty("endhour")
    private String endhour;
    @JsonProperty("active")
    private String active;
    @JsonProperty("address")
    private String address;
    @JsonProperty("description")
    private String description;
    @JsonProperty("coordinate")
    private String coordinate;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sitiedefault")
    private String sitiedefault;
    @JsonProperty("distance")
    private double distance;
    @JsonProperty("date_save")
    private String date_save;
    @JsonProperty("date_update")
    private String date_update;

    public SesionTrainer() {
    }

    public SesionTrainer(Long id, Long idgroup, Long iduser, String startday, String endday, String starthour, String endhour, String active, String address, String description, String coordinate) {
        this.id = id;
        this.idgroup = idgroup;
        this.iduser = iduser;
        this.startday = startday;
        this.endday = endday;
        this.starthour = starthour;
        this.endhour = endhour;
        this.active = active;
        this.address = address;
        this.description = description;
        this.coordinate = coordinate;
    }
}
