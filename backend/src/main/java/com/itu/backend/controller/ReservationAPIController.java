package com.itu.backend.controller;

import com.itu.backend.dto.ReservationDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/reservations")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationAPIController {

    private final RestTemplate restTemplate;
    // URL de l' API Back-Office
    private final String API_URL = "http://localhost:8080/location/api/reservations";

    public ReservationAPIController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public static class ApiResponse {
        public List<ReservationDTO> reservations;
        public boolean success;
    }

    @GetMapping("/list")
    public ApiResponse listReservations(
            @RequestParam(value = "filterDate", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate filterDate) {
        // 1. Appel de l'API distante
        ApiResponse responseGot = restTemplate.getForObject(API_URL, ApiResponse.class);
        List<ReservationDTO> allReservations = (responseGot != null) ? responseGot.reservations : List.of();
        boolean succes = responseGot.success;        
        // 2. Logique de filtrage
        if (filterDate != null) {
            allReservations = allReservations.stream()
                .filter(res -> res.dateHeure() != null && res.dateHeure().toLocalDate().equals(filterDate))
                .collect(Collectors.toList());
        }
        ApiResponse responseSend = new ApiResponse();
        responseSend.reservations = allReservations;
        responseSend.success = succes; 
        return responseSend;
    }
}