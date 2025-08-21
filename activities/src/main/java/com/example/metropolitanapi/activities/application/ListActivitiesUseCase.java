package com.example.metropolitanapi.activities.application;

import com.example.metropolitanapi.activities.domain.model.Activity;
import com.example.metropolitanapi.activities.domain.port.ActivityRepositoryPort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ListActivitiesUseCase {
    private final ActivityRepositoryPort repo;

    public ListActivitiesUseCase(ActivityRepositoryPort repo) {
        this.repo = repo;
    }

    public List<Activity> execute(Long spaceId){
        return repo.findByOptionalSpace(spaceId);
    }
}