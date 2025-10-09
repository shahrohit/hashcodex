package com.shahrohit.hashcodex.entities;

import com.shahrohit.hashcodex.enums.ProblemDifficulty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "problems")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{field.required}")
    @Column(unique = true, nullable = false)
    @Positive(message = "{field.positive}")
    private Integer number;

    @NotBlank(message = "{field.required}")
    @Size(min =  2, max = 100, message = "{field.size2_100}")
    @Column(unique = true, nullable = false, length = 100)
    private String slug;

    @NotBlank(message = "{field.required}")
    @Size(min =  2, max = 100, message = "{field.size2_100}")
    @Column(nullable = false, length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProblemDifficulty difficulty;

    @NotBlank(message = "{field.required}")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "{field.required}")
    @Size(max = 100, message = "{field.max100}")
    @Column(nullable = false, length = 100)
    private String params;

    @Column(nullable = false)
    private Boolean active;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;
}
