package com.shahrohit.hashcodex.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "problem_testcases")
public class ProblemTestcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Problem problem;

    @Column(nullable = false)
    private Boolean sample;

    @NotBlank(message = "{field.required}")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String input;

    @NotBlank(message = "{field.required}")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String output;
}
