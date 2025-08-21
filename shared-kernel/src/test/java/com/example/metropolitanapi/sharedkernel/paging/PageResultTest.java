package com.example.metropolitanapi.sharedkernel.paging;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class PageResultTest {

    @Test
    void holds_values_correctly() {
        // PageResult(List<T> items, int limit, int offset, long total)
        PageResult<String> page = new PageResult<>(List.of("a", "b"), 10, 20, 42L);

        assertThat(page.limit()).isEqualTo(10);
        assertThat(page.offset()).isEqualTo(20);
        assertThat(page.total()).isEqualTo(42L);
        assertThat(page.items()).containsExactly("a", "b");
    }
}
