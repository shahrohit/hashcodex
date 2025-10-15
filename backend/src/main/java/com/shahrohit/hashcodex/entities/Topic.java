package com.shahrohit.hashcodex.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{field.required}")
    @Size(min =  2, max = 50, message = "{field.size2_50}")
    @Column(unique = true, nullable = false, length = 50)
    private String slug;

    @NotBlank(message = "{field.required}")
    @Size(min =  2, max = 50, message = "{field.size2_50}")
    @Column(nullable = false, length = 50)
    private String name;
}
