package com.example.apibikerental.controller;

import com.example.apibikerental.model.Bike;
import com.example.apibikerental.model.Booking;
import com.example.apibikerental.model.CustomResponse;
import com.example.apibikerental.model.UserSelection;
import com.example.apibikerental.security.JwtUtil;
import com.example.apibikerental.service.AdminService;
import com.example.apibikerental.service.BookingService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add-bike")
    public ResponseEntity<?> addBike(@RequestHeader("Authorization") String token, @RequestBody Bike bike) {
        Claims claims = validateToken(token);
        if (claims == null || !Objects.equals(claims.get("role"), "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse("Unauthorized access: Invalid token or role", null));
        }
        adminService.addBike(bike);
        return ResponseEntity.ok(createSuccessResponse("Bike added successfully", null));
    }

    @GetMapping("/bikes")
    public ResponseEntity<?> getBikes(@RequestHeader("Authorization") String token) {
        Claims claims = validateToken(token);
        if (claims == null || !Objects.equals(claims.get("role"), "ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse("Unauthorized access: Invalid token or role", null));
        }
        List<Bike> bikes = adminService.getBikes();
        return ResponseEntity.ok(createSuccessResponse("Bikes fetched successfully", bikes));
    }

    @GetMapping("/user-selections")
    public ResponseEntity<?> getUserSelections(@RequestHeader("Authorization") String token) {
        if (validateToken(token) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse("Unauthorized access: Invalid token", null));
        }
        List<UserSelection> userSelections = adminService.getUserSelections();
        return ResponseEntity.ok(createSuccessResponse("User selections fetched successfully", userSelections));
    }

    private Claims validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    private CustomResponse createSuccessResponse(String message, Object data) {
        return new CustomResponse(HttpStatus.OK.value(), message, data);
    }

    private CustomResponse createErrorResponse(String message, Object data) {
        return new CustomResponse(HttpStatus.UNAUTHORIZED.value(), message, data);
    }
}
