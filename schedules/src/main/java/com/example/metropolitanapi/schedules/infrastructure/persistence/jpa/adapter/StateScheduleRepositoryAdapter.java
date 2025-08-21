package com.example.metropolitanapi.schedules.infrastructure.persistence.jpa.adapter;

import com.example.metropolitanapi.schedules.domain.model.StateSchedule;
import com.example.metropolitanapi.schedules.domain.port.StateScheduleRepositoryPort;
import com.example.metropolitanapi.schedules.infrastructure.persistence.jpa.entity.StateScheduleEntity;
import com.example.metropolitanapi.schedules.infrastructure.persistence.jpa.spring.StateScheduleJpaRepository;
import org.springframework.stereotype.Component;


import java.util.Optional;


@Component
public class StateScheduleRepositoryAdapter implements StateScheduleRepositoryPort {
    private final StateScheduleJpaRepository repo;
    public StateScheduleRepositoryAdapter(StateScheduleJpaRepository repo){ this.repo = repo; }


    private StateSchedule toDomain(StateScheduleEntity e){
        return new StateSchedule(e.getId(), e.getName());
    }

    @Override public Optional<StateSchedule> findById(Long id){
        return repo.findById(id).map(this::toDomain);
    }

    @Override public Optional<StateSchedule> findByName(String name){
        return repo.findByName(name).map(this::toDomain);
    }
}