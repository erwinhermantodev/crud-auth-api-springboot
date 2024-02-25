package com.example.bikerentalapiwithspringboot.repository;

import com.example.bikerentalapiwithspringboot.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
