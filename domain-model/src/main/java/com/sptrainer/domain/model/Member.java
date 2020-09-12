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
        "iduser",
        "idgroup",
        "state",
        "date_save",
        "date_update",
        "n",
        "user",
        "grouptrainer",
        "calification"
})
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("iduser")
    private Long iduser;
    @JsonProperty("idgroup")
    private Long idgroup;
    @JsonProperty("state")
    private String state;
    @JsonProperty("date_save")
    private String date_save;
    @JsonProperty("date_update")
    private String date_update;
    @JsonProperty("n")
    private int n;
    @JsonProperty("user")
    private User user = new User();
    @JsonProperty("grouptrainer")
    private GroupTrainer grouptrainer = new GroupTrainer();
    @JsonProperty("calification")
    private Calification calification = new Calification();

    public Member() {
    }

    public Member(Long idgroup, int n) {
        this.idgroup = idgroup;
        this.n = n;
    }


}
