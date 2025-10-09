package com.shahrohit.hashcodex.entities;

import com.shahrohit.hashcodex.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(unique = true, nullable = false, updatable = false)
    protected UUID publicId;

    @NotBlank(message = "{name.required}")
    @Size(min = 2, max = 50, message = "{name.size}")
    @Column(nullable = false, length = 50)
    protected String name;

    @NotBlank(message = "{email.required}")
    @Email(message = "{email.invalid}")
    @Column(unique = true, nullable = false)
    protected String email;

    @Column(length = 100)
    protected String hashedPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean emailVerified;

    @Column(nullable = false)
    protected boolean enabled;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    protected Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    protected Instant updatedAt;

    @PrePersist
    protected void prePersist() {
        publicId = UUID.randomUUID();
        enabled = false;
    }
}
