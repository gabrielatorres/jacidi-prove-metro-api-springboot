package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListCalendarUseCaseTest {

    @Mock MemberRepositoryPort members;

    @Test
    void returns_all_when_state_null() {
        Member m = new Member(1L, "Luis", "3264759F", "Valencia",
                new ArrayList<>(List.of(new CalendarEntry(10L, 1L), new CalendarEntry(11L, 2L))));

        when(members.findById(1L)).thenReturn(Optional.of(m));

        ListCalendarUseCase uc = new ListCalendarUseCase(members);

        assertThat(uc.execute(1L, null))
                .containsExactlyElementsOf(m.getCalendar());
    }

    @Test
    void filters_by_state_id() {
        Member m = new Member(1L, "Luis", "3264759F", "Valencia",
                new ArrayList<>(List.of(new CalendarEntry(10L, 1L), new CalendarEntry(11L, 2L), new CalendarEntry(12L, 1L))));

        when(members.findById(1L)).thenReturn(Optional.of(m));

        ListCalendarUseCase uc = new ListCalendarUseCase(members);

        assertThat(uc.execute(1L, 1L))
                .containsExactly(new CalendarEntry(10L, 1L), new CalendarEntry(12L, 1L));
    }
}
