package com.example.metropolitanapi.activities.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity @Table(name = "activity")
public class ActivityEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate scheduled;
    @Column(name="spaces_id")
    private Long spaceId;

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public LocalDate getScheduled(){return scheduled;} public void setScheduled(LocalDate scheduled){this.scheduled=scheduled;}
    public Long getSpaceId(){return spaceId;} public void setSpaceId(Long spaceId){this.spaceId=spaceId;}
}