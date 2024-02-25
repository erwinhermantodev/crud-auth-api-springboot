package com.example.apibikerental.service;

import com.example.apibikerental.controller.AdminController;
import com.example.apibikerental.model.Bike;
import com.example.apibikerental.model.Booking;
import com.example.apibikerental.model.User;
import com.example.apibikerental.model.UserSelection;
import com.example.apibikerental.repository.BikeRepository;
import com.example.apibikerental.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

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

    public List<UserSelection> getUserSelections() {
        // Perform inner join operation between User and Booking entities
        List<Object[]> results = bookingRepository.findUserSelections();

        // Map the results to UserSelection objects
        List<UserSelection> userSelections = new ArrayList<>();
        for (Object[] row : results) {
            User user = (User) row[0];
            Booking booking = (Booking) row[1];
            userSelections.add(new UserSelection(user, booking));
        }

        return userSelections;
    }
}
