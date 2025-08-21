package com.example.metropolitanapi.activities.domain.port;

import com.example.metropolitanapi.activities.domain.model.Activity;
import java.util.*;


public interface ActivityRepositoryPort {
    Optional<Activity> findById(Long id);
    List<Activity> findByOptionalSpace(Long spaceId);
    List<Activity> findAllByIds(Collection<Long> ids);
}