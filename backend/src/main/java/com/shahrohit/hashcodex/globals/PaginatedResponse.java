package com.shahrohit.hashcodex.globals;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Paginated Response of the request
 */
public record PaginatedResponse<T>(
    List<T> items,
    int totalPages,
    long totalItems,
    int currentPage,
    int pageSize
) {

    /**
     * Build {@link PaginatedResponse} from {@link Page} data
     */
    public static <T> PaginatedResponse<T> build(Page<T> page) {
        return new PaginatedResponse<>(page.getContent(),
            page.getTotalPages(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }
}
