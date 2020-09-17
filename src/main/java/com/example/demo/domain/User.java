package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    @Size(max = 128, min = 1)
    private String name;
    @Min(16)
    private long age;
    @Size(max = 512, min = 8)
    private String avatar;
    @Size(max = 1024, min = 0)
    private String description;

    // GTB: - 需求说明里没有要求包含 educationList
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Education> educationList;
}
