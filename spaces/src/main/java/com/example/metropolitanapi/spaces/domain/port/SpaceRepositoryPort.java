package com.example.metropolitanapi.spaces.domain.port;

import com.example.metropolitanapi.spaces.domain.model.Space;
import java.util.Optional;

public interface SpaceRepositoryPort {
    Optional<Space> findById(Long id);
}