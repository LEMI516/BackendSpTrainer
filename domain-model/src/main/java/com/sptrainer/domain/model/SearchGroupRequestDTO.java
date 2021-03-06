package com.sptrainer.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "idusers",
        "filter",
        "categories"
})
public class SearchGroupRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("iduser")
    private List<Long> iduser;
    @JsonProperty("filter")
    private String filter;
    @JsonProperty("category")
    private List<String> category;
}
