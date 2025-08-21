package com.example.metropolitanapi.api.web.dto;

import jakarta.validation.constraints.*;

public record MemberUpsertRequest(
        @NotBlank String name,
        @NotBlank String dni,
        @NotBlank String city
) {}