package com.example.metropolitanapi.spaces.infrastructure.persistence.jpa.spring;

import com.example.metropolitanapi.spaces.infrastructure.persistence.jpa.entity.SpaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpaceJpaRepository extends JpaRepository<SpaceEntity, Long> {}