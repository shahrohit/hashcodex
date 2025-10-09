package com.shahrohit.hashcodex.entities;

import com.shahrohit.hashcodex.enums.Language;
import com.shahrohit.hashcodex.enums.ProblemSubmissionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "problem_submissions")
public class ProblemSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Problem problem;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Language language;

    @NotBlank(message = "{field.required}")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProblemSubmissionStatus status;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant submittedAt;
}
