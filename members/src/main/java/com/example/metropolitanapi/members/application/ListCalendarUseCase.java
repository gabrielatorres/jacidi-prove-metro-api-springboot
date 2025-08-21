package com.example.metropolitanapi.members.application;


import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.sharedkernel.exception.NotFoundException;
import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class ListCalendarUseCase {
    private final MemberRepositoryPort members;
    public ListCalendarUseCase(MemberRepositoryPort members){ this.members = members; }


    /**
     * Lista el calendario de un miembro. Si {@code stateIdOrNull} != null, filtra por ese state_id.
     */
    public List<CalendarEntry> execute(Long memberId, Long stateIdOrNull){
        Member m = members.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado: "+memberId));


        if (stateIdOrNull == null) return m.getCalendar();


        List<CalendarEntry> out = new ArrayList<>();
        for (CalendarEntry ci : m.getCalendar()) {
            if (Objects.equals(ci.stateId(), stateIdOrNull)) out.add(ci);
        }
        return out;
    }
}