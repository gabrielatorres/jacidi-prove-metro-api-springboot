package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.ActivityQueryPort;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.members.domain.port.StateQueryPort;
import com.example.metropolitanapi.sharedkernel.exception.ConflictException;
import com.example.metropolitanapi.sharedkernel.model.ActivitySnapshot;
import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import com.example.metropolitanapi.sharedkernel.model.StateRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignActivityUseCaseTest {

    @Mock MemberRepositoryPort members;
    @Mock ActivityQueryPort activities;
    @Mock StateQueryPort states;
    @InjectMocks AssignActivityUseCase useCase;

    Member baseMember;

    @BeforeEach
    void setUp(){
        baseMember = new Member(1L, "Luis", "3264759F", "Valencia", new ArrayList<>());
    }

    @Test
    void rejects_duplicate_activity() {
        baseMember.setCalendar(List.of(new CalendarEntry(7L, 1L)));

        when(members.findById(1L)).thenReturn(Optional.of(baseMember));
        when(activities.findById(7L)).thenReturn(Optional.of(new ActivitySnapshot(7L, "spin", LocalDate.of(2025,1,7), 3L)));

        assertThatThrownBy(() -> useCase.execute(1L, 7L, 1L))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("ya está asignada");

        verify(members, never()).save(any());
    }

    @Test
    void rejects_date_clash_ignoring_canceled() {
        baseMember.setCalendar(List.of(new CalendarEntry(10L, 1L), new CalendarEntry(11L, 2L)));

        when(members.findById(1L)).thenReturn(Optional.of(baseMember));
        when(activities.findById(9L)).thenReturn(Optional.of(new ActivitySnapshot(9L, "zumba", LocalDate.of(2025,1,7), 4L)));
        when(activities.findAllByIds(new LinkedHashSet<>(List.of(10L,11L))))
                .thenReturn(List.of(
                        new ActivitySnapshot(10L, "spin", LocalDate.of(2025,1,7), 3L),
                        new ActivitySnapshot(11L, "yoga", LocalDate.of(2025,1,7), 4L)
                ));
        when(states.findByName("cancelar")).thenReturn(Optional.of(new StateRef(2L, "cancelar")));

        assertThatThrownBy(() -> useCase.execute(1L, 9L, 1L))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("misma fecha");

        verify(members, never()).save(any());
    }

    @Test
    void assigns_success_with_given_state_id() {
        baseMember.setCalendar(List.of(new CalendarEntry(10L, 2L))); // cancelada, distinta fecha

        when(members.findById(1L)).thenReturn(Optional.of(baseMember));
        when(activities.findById(9L)).thenReturn(Optional.of(new ActivitySnapshot(9L, "zumba", LocalDate.of(2025,1,8), 4L)));
        when(activities.findAllByIds(any())).thenReturn(List.of(
                new ActivitySnapshot(10L, "spin", LocalDate.of(2025,1,7), 3L)
        ));
        when(states.findByName("cancelar")).thenReturn(Optional.of(new StateRef(2L, "cancelar")));
        when(members.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Member out = useCase.execute(1L, 9L, 3L);

        assertThat(out.getCalendar())
                .anyMatch(ci -> Objects.equals(ci.activityId(), 9L) && Objects.equals(ci.stateId(), 3L));
    }
}
