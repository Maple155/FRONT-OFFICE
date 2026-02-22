package com.itu.backend.controller;

import com.itu.backend.dto.ReservationDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final RestTemplate restTemplate;
    // URL de ton API Back-Office
    private final String API_URL = "http://localhost:8080/location/api/reservations";

    public ReservationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Classe interne pour mapper le JSON {"reservations": [...], "success": true, ...}
    private static class ApiResponse {
        public List<ReservationDTO> reservations;
        public boolean success;
    }

    @GetMapping("/list")
    public String listReservations(
            @RequestParam(value = "filterDate", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate filterDate, 
            Model model) {
        
        // 1. Appel de l'API distante
        ApiResponse response = restTemplate.getForObject(API_URL, ApiResponse.class);
        List<ReservationDTO> allReservations = (response != null) ? response.reservations : List.of();

        // 2. Logique de filtrage
        if (filterDate != null) {
            allReservations = allReservations.stream()
                .filter(res -> res.dateHeure() != null && res.dateHeure().toLocalDate().equals(filterDate))
                .collect(Collectors.toList());
        }

        model.addAttribute("reservations", allReservations);
        model.addAttribute("selectedDate", filterDate);
        
        return "reservations";
    }
}