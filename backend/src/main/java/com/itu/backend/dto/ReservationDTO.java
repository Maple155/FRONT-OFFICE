package com.itu.backend.dto;

import java.time.LocalDateTime;

public record ReservationDTO(
    Integer id,
    Integer idLieu,
    String client,
    Integer nbPassager,
    LocalDateTime dateHeure,
    String lieuCode
) {}