package com.example.metropolitanapi.schedules.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;

@Entity @Table(name = "states_schedule")
public class StateScheduleEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId(){ return id; } public void setId(Long id){ this.id=id; }
    public String getName(){ return name; } public void setName(String name){ this.name=name; }

}