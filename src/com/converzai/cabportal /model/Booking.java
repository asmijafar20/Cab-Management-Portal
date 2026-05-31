package com.converzai.cabportal.model;

import java.time.LocalDateTime;

public class Booking {
    private final String bookingId;
    private final String cabId;
    private final String cityId;
    private final LocalDateTime bookedAt;

    public Booking(String bookingId, String cabId, String cityId, LocalDateTime bookedAt) {
        this.bookingId = bookingId;
        this.cabId = cabId;
        this.cityId = cityId;
        this.bookedAt = bookedAt;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCabId() {
        return cabId;
    }

    public String getCityId() {
        return cityId;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }
}
