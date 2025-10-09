package com.shahrohit.hashcodex.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProblemTopicId implements Serializable {
    private Long problemId;
    private Long topicId;
}
