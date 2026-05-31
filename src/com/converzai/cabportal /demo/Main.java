package com.converzai.cabportal.demo;

import com.converzai.cabportal.model.Booking;
import com.converzai.cabportal.model.CabSnapshot;
import com.converzai.cabportal.model.CabState;
import com.converzai.cabportal.service.CabManagementPortal;

// runs a plain Java demo flow
public class Main {
    public static void main(String[] args) {
        CabManagementPortal portal = new CabManagementPortal();

        portal.onboardCity("HYD");
        portal.onboardCity("BLR");

        portal.registerCab("CAB-1", "HYD");
        portal.registerCab("CAB-2", "HYD");
        portal.registerCab("CAB-3", "BLR");

        Booking booking = portal.bookCab("HYD");
        System.out.println("Booked cab: " + booking.getCabId() + " from city " + booking.getCityId());

        portal.changeCabState(booking.getCabId(), CabState.IDLE);
        portal.changeCabCity(booking.getCabId(), "BLR");

        System.out.println("\nCab snapshots:");
        for (CabSnapshot snapshot : portal.getCabSnapshots()) {
            System.out.println(snapshot);
        }
    }
}
