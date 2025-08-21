package com.example.metropolitanapi.sharedkernel.paging;

import java.util.List;

public record PageResult<T>(List<T> items, int limit, int offset, long total) {}
