package com.shahrohit.hashcodex.entities;

import com.shahrohit.hashcodex.enums.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "problem_codes")
public class ProblemCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Problem problem;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Language language;

    @NotBlank(message = "{field.required}")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String driverCode;

    @NotBlank(message = "{field.required}")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String userCode;

    @NotBlank(message = "{field.required}")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String solutionCode;
}
