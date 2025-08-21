package com.example.metropolitanapi.activities.infrastructure.persistence.jpa.adapter;

import com.example.metropolitanapi.activities.domain.model.Activity;
import com.example.metropolitanapi.activities.domain.port.ActivityRepositoryPort;
import com.example.metropolitanapi.activities.infrastructure.persistence.jpa.entity.ActivityEntity;
import com.example.metropolitanapi.activities.infrastructure.persistence.jpa.spring.ActivityJpaRepository;
import org.springframework.stereotype.Component;


import java.util.*; import java.util.stream.Collectors;


@Component
public class ActivityRepositoryAdapter implements ActivityRepositoryPort {
    private final ActivityJpaRepository repo;

    public ActivityRepositoryAdapter(ActivityJpaRepository repo){
        this.repo = repo;
    }

    private Activity toDomain(ActivityEntity e){
        return new Activity(e.getId(), e.getName(), e.getScheduled(), e.getSpaceId());
    }

    @Override public Optional<Activity> findById(Long id){
        return repo.findById(id).map(this::toDomain);
    }

    @Override public List<Activity> findByOptionalSpace(Long spaceId){
        return repo.findByOptionalSpace(spaceId).stream().map(this::toDomain).toList();
    }

    @Override public List<Activity> findAllByIds(Collection<Long> ids){
        return repo.findAllById(ids).stream().map(this::toDomain).collect(Collectors.toList());
    }
}