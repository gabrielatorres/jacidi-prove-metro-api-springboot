package com.example.metropolitanapi.activities.domain.model;

import java.time.LocalDate;


public class Activity {
    private final Long id;
    private final String name;
    private final LocalDate scheduled;
    private final Long spaceId;

    public Activity(Long id, String name, LocalDate scheduled, Long spaceId){
        this.id=id; this.name=name; this.scheduled=scheduled; this.spaceId=spaceId;
    }
    public Long getId(){ return id; }
    public String getName(){ return name; }
    public LocalDate getScheduled(){ return scheduled; }
    public Long getSpaceId(){ return spaceId; }
}