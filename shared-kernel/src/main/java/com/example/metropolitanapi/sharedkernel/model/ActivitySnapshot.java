package com.example.metropolitanapi.sharedkernel.model;

import java.time.LocalDate;


/**
 * Proyección ligera de Activity para cruzar módulos sin acoplar dominios.
 */
public record ActivitySnapshot(Long id, String name, LocalDate scheduled, Long spacesId) {}