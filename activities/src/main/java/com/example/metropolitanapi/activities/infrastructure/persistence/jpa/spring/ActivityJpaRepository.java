package com.example.metropolitanapi.activities.infrastructure.persistence.jpa.spring;

import com.example.metropolitanapi.activities.infrastructure.persistence.jpa.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface ActivityJpaRepository extends JpaRepository<ActivityEntity, Long> {
    @Query("select a from ActivityEntity a where (:spaceId is null or a.spaceId = :spaceId)")
    List<ActivityEntity> findByOptionalSpace(Long spaceId);
}