package com.example.metropolitanapi.api.web.dto;

import jakarta.validation.constraints.NotNull;

public record AssignActivityRequest(
        @NotNull Long activityId,
        @NotNull Long stateId
) {}