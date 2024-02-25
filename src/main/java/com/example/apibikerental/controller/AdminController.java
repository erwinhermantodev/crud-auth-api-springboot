package com.example.bikerentalapiwithspringboot.controller;

import com.example.bikerentalapiwithspringboot.model.Bike;
import com.example.bikerentalapiwithspringboot.model.Booking;
import com.example.bikerentalapiwithspringboot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-bike")
    public ResponseEntity<?> addBike(@RequestBody Bike bike) {
        adminService.addBike(bike);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> getBikes() {
        List<Bike> bikes = adminService.getBikes();
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/user-selections")
    public ResponseEntity<List<Booking>> getUserSelections() {
        List<Booking> userSelections = adminService.getUserSelections();
        return ResponseEntity.ok(userSelections);
    }
}
