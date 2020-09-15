package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    private long userId;
    private long year;
    @Size(max = 256, min = 1)
    private String title;
    @Size(max = 4096, min = 1)
    private String description;
}
