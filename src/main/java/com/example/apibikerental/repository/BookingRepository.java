package com.example.apibikerental.repository;

import com.example.apibikerental.model.Booking;
import com.example.apibikerental.model.UserSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT u, b FROM User u INNER JOIN Booking b ON u.userID = b.user.userID")
    List<Object[]> findUserSelections();
}
