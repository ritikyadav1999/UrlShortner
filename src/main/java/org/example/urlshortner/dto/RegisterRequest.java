package org.example.urlshortner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        @Size(min = 2, max = 20)
        String name,

        @Email
        @NotBlank
        @Size(min = 8, max = 100)
        String email,

        @NotBlank
        @Size(min = 6)
        String password
) {
}
