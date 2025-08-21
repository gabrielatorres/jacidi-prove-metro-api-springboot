package com.example.metropolitanapi.spaces.infrastructure.persistence.jpa.adapter;

import com.example.metropolitanapi.spaces.domain.model.Space;
import com.example.metropolitanapi.spaces.domain.port.SpaceRepositoryPort;
import com.example.metropolitanapi.spaces.infrastructure.persistence.jpa.entity.SpaceEntity;
import com.example.metropolitanapi.spaces.infrastructure.persistence.jpa.spring.SpaceJpaRepository;
import org.springframework.stereotype.Component;


import java.util.Optional;


@Component
public class SpaceRepositoryAdapter implements SpaceRepositoryPort {
    private final SpaceJpaRepository repo;
    public SpaceRepositoryAdapter(SpaceJpaRepository repo){ this.repo=repo; }

    private Space toDomain(SpaceEntity e){
        return new Space(e.getId(), e.getName(), e.getDescription());
    }

    @Override public Optional<Space> findById(Long id){
        return repo.findById(id).map(this::toDomain);
    }
}