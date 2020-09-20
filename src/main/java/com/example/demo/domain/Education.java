package com.example.demo.domain;

import com.example.demo.serializer.UserSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties(value = "userId")
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private Long year;
    @Size(max = 256, min = 1)
    private String title;
    @Size(max = 4096, min = 1)
    private String description;

    @ManyToOne
    @JsonProperty("userId")
    @JoinColumn(name = "user_id")
    @JsonSerialize(using = UserSerializer.class)
    private User user;
}
