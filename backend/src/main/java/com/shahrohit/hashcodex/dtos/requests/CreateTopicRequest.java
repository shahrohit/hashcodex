package com.shahrohit.hashcodex.dtos.requests;

import com.shahrohit.hashcodex.utils.Helper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO to create topic
 */
public record CreateTopicRequest(
    @NotBlank(message = "{field.required}")
    @Size(min = 2, max = 50, message = "{field.size2_50}")
    String slug,

    @NotBlank(message = "{field.required}")
    @Size(min = 2, max = 50, message = "{field.size2_50}")
    String name
) {
    public CreateTopicRequest {
        if (slug != null) {
            slug = Helper.formateSlug(slug);
        }
    }
}
