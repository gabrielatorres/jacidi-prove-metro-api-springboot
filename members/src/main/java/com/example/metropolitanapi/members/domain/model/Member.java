package com.example.metropolitanapi.members.domain.model;

import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private Long id;
    private String name;
    private String dni;
    private String city;
    private List<CalendarEntry> calendar = new ArrayList<>();

    public Member() {
        this.calendar = new ArrayList<>();
    }

    public Member(Long id, String name, String dni, String city, List<CalendarEntry> calendar) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.city = city;
        this.calendar = (calendar != null) ? calendar : new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<CalendarEntry> getCalendar() { return calendar; }
    public void setCalendar(List<CalendarEntry> calendar) {
        this.calendar = (calendar != null) ? calendar : new ArrayList<>();
    }
}