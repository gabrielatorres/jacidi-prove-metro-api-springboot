package com.example.metropolitanapi.members.infrastructure.persistence.jpa.entity;

import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ autoincrement (PostgreSQL IDENTITY)
    private Long id;

    private String name;
    private String dni;
    private String city;

    @Type(JsonBinaryType.class)
    @Column(name = "calendario", columnDefinition = "jsonb", nullable = false)
    private List<CalendarEntry> calendar = new ArrayList<>();

    public MemberEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public List<CalendarEntry> getCalendar() { return calendar; }
    public void setCalendar(List<CalendarEntry> calendar) { this.calendar = calendar; }
}
