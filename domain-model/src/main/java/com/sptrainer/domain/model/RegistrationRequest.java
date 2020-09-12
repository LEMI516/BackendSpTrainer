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
        "iduser",
        "idgroup",
        "state",
        "comment",
        "user",
        "grouptrainer",
        "dateregistration",
        "answer",
        "dateanswer",
        "n"
})
public class RegistrationRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("iduser")
    private Long iduser;
    @JsonProperty("idgroup")
    private Long idgroup;
    @JsonProperty("state")
    private String state;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("dateregistration")
    private String dateregistration;
    @JsonProperty("answer")
    private String answer;
    @JsonProperty("dateanswer")
    private String dateanswer;
    @JsonProperty("n")
    private int n;
    @JsonProperty("user")
    private User user = new User();
    @JsonProperty("grouptrainer")
    private GroupTrainer grouptrainer = new GroupTrainer();

    public RegistrationRequest() {
    }

    public RegistrationRequest(Long idgroup, int n) {
        this.idgroup = idgroup;
        this.n = n;
    }


}
