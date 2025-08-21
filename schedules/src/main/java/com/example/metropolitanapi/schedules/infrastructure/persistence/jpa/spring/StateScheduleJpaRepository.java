package com.example.metropolitanapi.schedules.infrastructure.persistence.jpa.spring;

import com.example.metropolitanapi.schedules.infrastructure.persistence.jpa.entity.StateScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;

public interface StateScheduleJpaRepository extends JpaRepository<StateScheduleEntity, Long> {
    Optional<StateScheduleEntity> findByName(String name);
}