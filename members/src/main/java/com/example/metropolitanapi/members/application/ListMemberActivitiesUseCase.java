package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.ActivityQueryPort;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.sharedkernel.exception.NotFoundException;
import com.example.metropolitanapi.sharedkernel.model.ActivitySnapshot;
import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ListMemberActivitiesUseCase {
    private final MemberRepositoryPort members;
    private final ActivityQueryPort activities;

    public ListMemberActivitiesUseCase(MemberRepositoryPort members, ActivityQueryPort activities) {
        this.members = members; this.activities = activities;
    }

    /**
     * Devuelve las actividades del calendario de un miembro.
     */
    public List<ActivitySnapshot> execute(Long memberId, Long spacesIdOrNull){
        Member m = members.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado: "+memberId));

        // ids únicos en orden de aparición
        LinkedHashSet<Long> ids = new LinkedHashSet<>();
        for (CalendarEntry ci : m.getCalendar()) {
            if (ci.activityId() != null) ids.add(ci.activityId());
        }
        if (ids.isEmpty()) return List.of();

        List<ActivitySnapshot> list = activities.findAllByIds(ids);
        if (spacesIdOrNull != null) {
            list = list.stream().filter(a -> Objects.equals(a.spacesId(), spacesIdOrNull)).toList();
        }
        list = new ArrayList<>(list);
        list.sort(Comparator.comparing(ActivitySnapshot::scheduled).thenComparing(ActivitySnapshot::id));
        return list;
    }
}
