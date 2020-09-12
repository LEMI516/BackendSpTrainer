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
        "datelog"
})
public class LogViewgGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("idgroup")
    private Long idgroup;
    @JsonProperty("iduser")
    private Long iduser;
    @JsonProperty("datelog")
    private String datelog;

    public LogViewgGroup() {
    }

    public LogViewgGroup(Long id, Long idgroup, Long iduser, String datelog) {
        this.id = id;
        this.idgroup = idgroup;
        this.iduser = iduser;
        this.datelog = datelog;
    }
}
