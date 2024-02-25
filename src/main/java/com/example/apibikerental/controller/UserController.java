package com.example.apibikerental.controller;

import com.example.apibikerental.model.Booking;
import com.example.apibikerental.model.CustomResponse;
import com.example.apibikerental.model.User;
import com.example.apibikerental.model.Bike;
import com.example.apibikerental.security.JwtUtil;
import com.example.apibikerental.service.BikeService;
import com.example.apibikerental.service.UserService;
import com.example.apibikerental.service.BookingService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BikeService bikeService;


    @Autowired
    private BookingService bookingService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        CustomResponse response = new CustomResponse();
        try {
            userService.registerUser(user);
            response.setStatus(200);
            response.setMessage("User registered successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        CustomResponse response = new CustomResponse();
        try {
            String token = userService.login(user.getEmail(), user.getPassword());
            if (token != null) {
                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("token", token);

                response.setStatus(HttpStatus.OK.value());
                response.setMessage("User logged in successfully");
                response.setData(tokenMap);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setMessage("Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error logging in: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/book-bike")
    public ResponseEntity<?> bookBike(@RequestHeader("Authorization") String token, @RequestBody Booking bookingRequest) {
        Claims claims = validateToken(token);
        if (claims == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse("Unauthorized access: Invalid token or role", null));
        }

        // Assuming you have a service method to retrieve the bike by its ID
        Long userId = null;
        Object userIdObject = claims.get("user_id");
        if (userIdObject instanceof Integer) {
            userId = ((Integer) userIdObject).longValue();
        } else if (userIdObject instanceof Long) {
            userId = (Long) userIdObject;
        }
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse("User not found", null));
        }

        Bike bike = bikeService.getBikeById(bookingRequest.getId());
        if (bike == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse("Bike not found", null));
        }

        // Create a booking entity
        Booking booking = new Booking();
        booking.setUser(user); // Assuming claims.getSubject() gives the user's email
        booking.setBike(bike);
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());

        // Save the booking in your database
        Booking savedBooking = bookingService.saveBooking(booking);

        if (savedBooking == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse("Failed to book the bike", null));
        }

        return ResponseEntity.ok(createSuccessResponse("Bike booked successfully", savedBooking));
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
