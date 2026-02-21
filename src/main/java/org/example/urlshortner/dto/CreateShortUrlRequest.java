package org.example.urlshortner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateShortUrlRequest(
        @NotBlank
        @Size(min = 1, max = 2048)
        String originalUrl,

        @Size(min = 1, max = 255)
        String description
) {
}
