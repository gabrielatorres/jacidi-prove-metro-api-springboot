package com.example.metropolitanapi.spaces.domain.model;

import lombok.Getter;

@Getter
public class Space {
    private final Long id;
    private final String name;
    private final String description;

    public Space(Long id, String name, String description) {
        this.id=id;
        this.name=name;
        this.description=description;
    }

}