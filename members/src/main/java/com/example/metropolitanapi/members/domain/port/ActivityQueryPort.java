package com.example.metropolitanapi.members.domain.port;

import com.example.metropolitanapi.sharedkernel.model.ActivitySnapshot;
import java.util.*;


public interface ActivityQueryPort {
    Optional<ActivitySnapshot> findById(Long id);
    List<ActivitySnapshot> findAllByIds(Collection<Long> ids);
}