package com.torloksz.arethium.dto;

import jakarta.validation.constraints.Email;

public record RegisterDTO(
        String name,
        @Email
        String email,
        String password,
        String confirmPassword
) {
}
