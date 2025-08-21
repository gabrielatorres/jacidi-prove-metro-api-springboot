package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.members.domain.port.StateQueryPort;
import com.example.metropolitanapi.sharedkernel.exception.NotFoundException;
import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CancelActivityUseCase {
    private final MemberRepositoryPort members;
    private final StateQueryPort states;

    public CancelActivityUseCase(MemberRepositoryPort members, StateQueryPort states){
        this.members = members; this.states = states;
    }

    /**
     * Cancela (o cambia al estado indicado) la actividad del calendario.
     * Si {@code cancelStateIdOrNull} es null, se usa el estado por defecto "cancelar".
     */
    @Transactional
    public Member execute(Long memberId, Long activityId, Long cancelStateIdOrNull){
        Member m = members.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado"));

        Long cancelId = (cancelStateIdOrNull != null)
                ? cancelStateIdOrNull
                : states.findByName("cancelar").orElseThrow(() -> new NotFoundException("Estado 'cancelar' no existe")).id();

        List<CalendarEntry> newCal = new ArrayList<>();
        boolean found = false;
        for (CalendarEntry ci : m.getCalendar()) {
            if (Objects.equals(ci.activityId(), activityId)) {
                found = true;
                newCal.add(new CalendarEntry(ci.activityId(), cancelId));
            } else {
                newCal.add(ci);
            }
        }
        if (!found) throw new NotFoundException("La actividad no está en el calendario del miembro");

        m.setCalendar(newCal);
        return members.save(m);
    }
}
