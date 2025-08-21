package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.ActivityQueryPort;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.members.domain.port.StateQueryPort;
import com.example.metropolitanapi.sharedkernel.exception.ConflictException;
import com.example.metropolitanapi.sharedkernel.exception.NotFoundException;
import com.example.metropolitanapi.sharedkernel.model.ActivitySnapshot;
import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import com.example.metropolitanapi.sharedkernel.model.StateRef;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.*;


@Service
public class AssignActivityUseCase {
    private final MemberRepositoryPort members;
    private final ActivityQueryPort activities;
    private final StateQueryPort states;


    public AssignActivityUseCase(MemberRepositoryPort members,
                                 ActivityQueryPort activities,
                                 StateQueryPort states) {
        this.members = members; this.activities = activities; this.states = states;
    }


    /**
     * Asignar una actividad al calendario con un state_id específico (obligatorio).
     */
    @Transactional
    public Member execute(Long memberId, Long activityId, Long stateId){
        Member m = members.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado"));
        ActivitySnapshot newAct = activities.findById(activityId)
                .orElseThrow(() -> new NotFoundException("Actividad no encontrada"));


// 1) No duplicar actividad
        boolean already = m.getCalendar().stream().anyMatch(ci -> Objects.equals(ci.activityId(), activityId));
        if (already) throw new ConflictException("La actividad ya está asignada al miembro");


// 2) No choque por fecha (ignorando canceladas)
        LocalDate date = newAct.scheduled();
        Set<Long> ids = new HashSet<>(); m.getCalendar().forEach(ci -> ids.add(ci.activityId()));
        List<ActivitySnapshot> current = activities.findAllByIds(ids);


        Long canceledId = states.findByName("cancelar").map(StateRef::id).orElse(-1L);
        boolean clash = m.getCalendar().stream().anyMatch(ci -> {
            if (Objects.equals(ci.stateId(), canceledId)) return false;
            ActivitySnapshot snap = current.stream().filter(a -> Objects.equals(a.id(), ci.activityId())).findFirst().orElse(null);
            return snap != null && snap.scheduled().isEqual(date);
        });
        if (clash) throw new ConflictException("Existe otra actividad en la misma fecha");


// 3) Añadir entrada con el state_id recibido
        List<CalendarEntry> cal = new ArrayList<>(m.getCalendar());
        cal.add(new CalendarEntry(activityId, stateId));
        m.setCalendar(cal);
        return members.save(m);
    }
}