package com.example.metropolitanapi.spaces.infrastructure.persistence.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SpaceEntityTest {
    @Test
    void getters_and_setters_work() {
        SpaceEntity e = new SpaceEntity();
        e.setId(7L);
        e.setName("cycling");
        e.setDescription("aula cycling/spinning");

        assertThat(e.getId()).isEqualTo(7L);
        assertThat(e.getName()).isEqualTo("cycling");
        assertThat(e.getDescription()).isEqualTo("aula cycling/spinning");
    }
}
