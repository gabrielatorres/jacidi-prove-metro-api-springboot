package com.example.metropolitanapi.members.application;

import com.example.metropolitanapi.members.domain.model.Member;
import com.example.metropolitanapi.members.domain.port.ActivityQueryPort;
import com.example.metropolitanapi.members.domain.port.MemberRepositoryPort;
import com.example.metropolitanapi.sharedkernel.model.ActivitySnapshot;
import com.example.metropolitanapi.sharedkernel.model.CalendarEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMemberActivitiesUseCaseTest {

    @Mock MemberRepositoryPort members;
    @Mock ActivityQueryPort activities;

    @Test
    void returns_sorted_and_filtered_by_spaces_if_present() {
        Member m = new Member(1L, "Luis", "3264759F", "Valencia",
                new ArrayList<>(List.of(new CalendarEntry(3L, 1L), new CalendarEntry(1L, 1L), new CalendarEntry(2L, 4L))));

        when(members.findById(1L)).thenReturn(Optional.of(m));

        when(activities.findAllByIds(new LinkedHashSet<>(List.of(3L, 1L, 2L))))
                .thenReturn(List.of(
                        new ActivitySnapshot(2L, "zumba", LocalDate.of(2025, 1, 9), 4L),
                        new ActivitySnapshot(1L, "padel", LocalDate.of(2025, 1, 7), 2L),
                        new ActivitySnapshot(3L, "spin",  LocalDate.of(2025, 1, 7), 3L)
                ));

        ListMemberActivitiesUseCase uc = new ListMemberActivitiesUseCase(members, activities);

        List<ActivitySnapshot> all = uc.execute(1L, null);
        assertThat(all).containsExactly(
                new ActivitySnapshot(1L, "padel", LocalDate.of(2025,1,7), 2L),
                new ActivitySnapshot(3L, "spin",  LocalDate.of(2025,1,7), 3L),
                new ActivitySnapshot(2L, "zumba", LocalDate.of(2025,1,9), 4L)
        );

        List<ActivitySnapshot> only3 = uc.execute(1L, 3L);
        assertThat(only3).containsExactly(
                new ActivitySnapshot(3L, "spin",  LocalDate.of(2025,1,7), 3L)
        );
    }
}
