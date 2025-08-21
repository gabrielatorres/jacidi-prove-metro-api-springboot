package com.example.metropolitanapi.members.domain.port;

import com.example.metropolitanapi.sharedkernel.model.StateRef;
import java.util.Optional;


public interface StateQueryPort {
    Optional<StateRef> findByName(String name);
}