package com.example.bikerentalapiwithspringboot.service;

import com.example.bikerentalapiwithspringboot.model.Bike;
import com.example.bikerentalapiwithspringboot.model.Booking;
import com.example.bikerentalapiwithspringboot.repository.BikeRepository;
import com.example.bikerentalapiwithspringboot.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public void addBike(Bike bike) {
        // implement adding bike logic
        bikeRepository.save(bike);
    }

    public List<Bike> getBikes() {
        // implement fetching bikes logic
        return bikeRepository.findAll();
    }

    public List<Booking> getUserSelections() {
        // implement fetching user selections logic
        return bookingRepository.findAll();
    }

    // implement other admin-related services
}
