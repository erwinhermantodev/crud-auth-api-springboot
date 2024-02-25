package com.example.apibikerental.model;

public class UserSelection {
    private User user;
    private Booking booking;

    public UserSelection(User user, Booking booking) {
        this.user = user;
        this.booking = booking;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

}
