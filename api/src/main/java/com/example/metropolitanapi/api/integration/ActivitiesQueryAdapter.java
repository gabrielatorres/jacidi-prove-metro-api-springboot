package com.example.metropolitanapi.api.integration;

import com.example.metropolitanapi.members.domain.port.ActivityQueryPort;
import com.example.metropolitanapi.sharedkernel.model.ActivitySnapshot;
import org.springframework.stereotype.Component;

import com.example.metropolitanapi.activities.infrastructure.persistence.jpa.entity.ActivityEntity;
import com.example.metropolitanapi.activities.infrastructure.persistence.jpa.spring.ActivityJpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class ActivitiesQueryAdapter implements ActivityQueryPort {

    private final ActivityJpaRepository jpa;

    public ActivitiesQueryAdapter(ActivityJpaRepository jpa) {
        this.jpa = jpa;
    }

    private ActivitySnapshot toSnap(ActivityEntity e) {
        return new ActivitySnapshot(
                e.getId(),
                e.getName(),
                e.getScheduled(),
                e.getSpaceId()
        );
    }

    @Override
    public Optional<ActivitySnapshot> findById(Long id) {
        return jpa.findById(id).map(this::toSnap);
    }

    @Override
    public List<ActivitySnapshot> findAllByIds(Collection<Long> ids) {
        List<ActivityEntity> found = jpa.findAllById(ids);
        List<ActivitySnapshot> out = new ArrayList<>(found.size());
        for (ActivityEntity e : found) out.add(toSnap(e));
        return out;
    }
}
