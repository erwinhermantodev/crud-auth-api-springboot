package com.example.bikerentalapiwithspringboot.repository;

import com.example.bikerentalapiwithspringboot.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
}
