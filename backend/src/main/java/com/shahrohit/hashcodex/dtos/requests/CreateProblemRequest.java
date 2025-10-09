package com.shahrohit.hashcodex.dtos.requests;


import com.shahrohit.hashcodex.enums.ProblemDifficulty;
import com.shahrohit.hashcodex.utils.Helper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProblemRequest(
    @NotBlank(message = "{field.required}")
    @Size(min = 2, max = 100, message = "{field.size2_100}")
    String slug,

    @NotBlank(message = "{field.required}")
    @Size(min = 2, max = 100, message = "{field.size2_100}")
    String title,

    @NotNull(message = "{field.required}")
    ProblemDifficulty difficulty,

    @NotBlank(message = "{field.required}")
    String description,

    @NotBlank(message = "{field.required}")
    @Size(max = 100, message = "{field.max100}")
    String params
) {
    public CreateProblemRequest {
        if (slug != null) {
            slug = Helper.formateSlug(slug);
        }
    }
}
