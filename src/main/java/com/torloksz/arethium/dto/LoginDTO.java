package com.torloksz.arethium.dto;

import jakarta.validation.constraints.Email;

public record LoginDTO(
        @Email
        String email,
        String password
) {
}
