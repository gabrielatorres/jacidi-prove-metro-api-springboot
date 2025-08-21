package com.example.metropolitanapi.sharedkernel.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public record CalendarEntry(
        @JsonProperty("actividad_id") Long activityId,
        @JsonProperty("state") Long stateId
) {}