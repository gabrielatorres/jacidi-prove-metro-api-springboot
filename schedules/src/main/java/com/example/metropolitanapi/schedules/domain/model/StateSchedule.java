package com.example.metropolitanapi.schedules.domain.model;

import lombok.Getter;

@Getter
public class StateSchedule {
    private final Long id;
    private final String name;

    public StateSchedule(Long id, String name) {
        this.id=id;
        this.name=name;
    }

}