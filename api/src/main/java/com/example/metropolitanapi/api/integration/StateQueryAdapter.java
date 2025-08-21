package com.example.metropolitanapi.api.integration;

import com.example.metropolitanapi.members.domain.port.StateQueryPort;
import com.example.metropolitanapi.sharedkernel.model.StateRef;
import com.example.metropolitanapi.schedules.domain.port.StateScheduleRepositoryPort;
import org.springframework.stereotype.Component;


import java.util.Optional;


@Component
public class StateQueryAdapter implements StateQueryPort {
    private final StateScheduleRepositoryPort states;
    public StateQueryAdapter(StateScheduleRepositoryPort states){ this.states = states; }

    @Override public Optional<StateRef> findByName(String name){
        return states.findByName(name).map(s -> new StateRef(s.getId(), s.getName()));
    }
}