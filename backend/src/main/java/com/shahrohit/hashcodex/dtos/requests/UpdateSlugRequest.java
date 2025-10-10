package com.shahrohit.hashcodex.dtos.requests;

import com.shahrohit.hashcodex.utils.Helper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO to update slug
 */
public record UpdateSlugRequest(
    @NotBlank(message = "{field.required}")
    @Size(min = 2, max = 100, message = "{field.size2_100}")
    String slug
) {
    public UpdateSlugRequest {
        if (slug != null) {
            slug = Helper.formateSlug(slug);
        }
    }
}
