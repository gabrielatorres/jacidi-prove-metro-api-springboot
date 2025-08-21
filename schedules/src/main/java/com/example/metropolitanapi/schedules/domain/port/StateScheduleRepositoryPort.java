package com.example.metropolitanapi.schedules.domain.port;

import com.example.metropolitanapi.schedules.domain.model.StateSchedule;
import java.util.Optional;

public interface StateScheduleRepositoryPort {
    Optional<StateSchedule> findById(Long id);
    Optional<StateSchedule> findByName(String name);
}