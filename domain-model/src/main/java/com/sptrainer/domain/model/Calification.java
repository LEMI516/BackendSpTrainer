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
        "idgroup",
        "iduser",
        "idsesion",
        "score",
        "idusercalificate",
        "datecalificate",
        "scoretotal"
})
public class Calification implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("idgroup")
    private Long idgroup;
    @JsonProperty("iduser")
    private Long iduser;
    @JsonProperty("idsesion")
    private Long idsesion;
    @JsonProperty("score")
    private Long score;
    @JsonProperty("scoretotal")
    private Double scoretotal;
    @JsonProperty("datecalificate")
    private String datecalificate;
    @JsonProperty("idusercalificate")
    private Long idusercalificate;
    private GroupTrainer groupTrainer;
    private User user;
    private SesionTrainer sesionTrainer;

    public Calification() {
    }

    public Calification(Long iduser,Long idgroup,Long score,Long idusercalificate){
        this.idgroup=idgroup;
        this.iduser=iduser;
        this.score=score;
        this.idusercalificate=idusercalificate;
    }

    public Calification(Long iduser,Long idgroup,Double scoretotal,Long idusercalificate){
        this.idgroup=idgroup;
        this.iduser=iduser;
        this.scoretotal=scoretotal;
        this.idusercalificate=idusercalificate;
    }

    public Calification(Long iduser,Double scoretotal){
        this.iduser=iduser;
        this.scoretotal=scoretotal;
        this.score=Long.valueOf((int)scoretotal.doubleValue());
    }

    public Calification(Long id,Long iduser,Long idgroup,Long score,Long idusercalificate,Double scoretotal){
        this.id=id;
        this.idgroup=idgroup;
        this.iduser=iduser;
        this.score=score;
        this.idusercalificate=idusercalificate;
        this.scoretotal=scoretotal;
    }


}
