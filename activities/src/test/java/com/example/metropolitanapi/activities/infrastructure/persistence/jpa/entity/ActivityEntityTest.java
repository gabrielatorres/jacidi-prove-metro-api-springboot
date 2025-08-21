package com.example.metropolitanapi.activities.infrastructure.persistence.jpa.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.*;

class ActivityEntityTest {
    @Test
    void getters_and_setters_work() {
        ActivityEntity e = new ActivityEntity();
        e.setId(5L);
        e.setName("zumba");
        e.setScheduled(LocalDate.of(2025,1,7));
        e.setSpaceId(3L);

        assertThat(e.getId()).isEqualTo(5L);
        assertThat(e.getName()).isEqualTo("zumba");
        assertThat(e.getScheduled()).isEqualTo(LocalDate.of(2025,1,7));
        assertThat(e.getSpaceId()).isEqualTo(3L);
    }
}
