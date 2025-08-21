package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.members.domain.port.StateQueryPort;
import com.example.metropolitanapi.sharedkernel.exception.NotFoundException;
import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import com.example.metropolitanapi.sharedkernel.model.StateRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelActivityUseCaseTest {

    @Mock MemberRepositoryPort members;
    @Mock StateQueryPort states;
    @InjectMocks CancelActivityUseCase useCase;

    Member member;

    @BeforeEach
    void setup(){
        member = new Member(1L, "Luis", "3264759F", "Valencia", new ArrayList<>());
        member.setCalendar(new ArrayList<>(List.of(new CalendarEntry(10L, 1L))));
    }

    @Test
    void cancel_with_provided_state_id() {
        when(members.findById(1L)).thenReturn(Optional.of(member));
        when(members.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Member out = useCase.execute(1L, 10L, 4L);

        assertThat(out.getCalendar()).containsExactly(new CalendarEntry(10L, 4L));
    }

    @Test
    void cancel_with_default_state_cancelar() {
        when(members.findById(1L)).thenReturn(Optional.of(member));
        when(states.findByName("cancelar")).thenReturn(Optional.of(new StateRef(2L, "cancelar")));
        when(members.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Member out = useCase.execute(1L, 10L, null);

        assertThat(out.getCalendar()).containsExactly(new CalendarEntry(10L, 2L));
    }

    @Test
    void throws_not_found_when_activity_absent() {
        when(members.findById(1L)).thenReturn(Optional.of(member));

        assertThatThrownBy(() -> useCase.execute(1L, 99L, 4L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("no está en el calendario");

        verify(members, never()).save(any());
    }
}
