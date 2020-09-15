package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private long age;
    private String avatar;
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Education> educationList;
}
