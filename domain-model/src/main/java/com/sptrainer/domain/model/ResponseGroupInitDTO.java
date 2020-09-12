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
        "categories",
        "users",
        "groups"
})
public class ResponseGroupInitDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("categories")
    private List<CategoryDTO> categories;
    @JsonProperty("users")
    private List<User> users;
    @JsonProperty("groups")
    private List<GroupTrainer> groups;

}
