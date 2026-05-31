package com.converzai.cabportal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.converzai.cabportal.model.Booking;
import com.converzai.cabportal.model.CabHistoryEntry;
import com.converzai.cabportal.model.CabSnapshot;
import com.converzai.cabportal.model.CabState;
import com.converzai.cabportal.service.CabManagementPortal;

public class CabManagementPortalTest {
    @Test
    void shouldRegisterCabsAndReturnSnapshots() {
        CabManagementPortal portal = new CabManagementPortal();

        portal.onboardCity("HYD");
        portal.registerCab("CAB-1", "HYD");

        List<CabSnapshot> snapshots = portal.getCabSnapshots();
        assertEquals(1, snapshots.size(), "Snapshot count should be 1");
        assertEquals("CAB-1", snapshots.get(0).getCabId(), "Cab id should match");
        assertEquals(CabState.IDLE, snapshots.get(0).getCabState(), "Cab should start as IDLE");
        assertEquals("HYD", snapshots.get(0).getCityId(), "Cab city should match");
    }

    @Test
    void shouldBookLongestIdleCab() {
        CabManagementPortal portal = new CabManagementPortal();

        portal.onboardCity("HYD");
        portal.registerCab("CAB-1", "HYD");
        pauseBriefly();
        portal.registerCab("CAB-2", "HYD");

        Booking booking = portal.bookCab("HYD");
        assertEquals("CAB-1", booking.getCabId(), "Longest idle cab should be booked first");
    }

    @Test
    void shouldBookSecondCabWhenFirstIsUnavailable() {
        CabManagementPortal portal = new CabManagementPortal();

        portal.onboardCity("HYD");
        portal.registerCab("CAB-1", "HYD");
        portal.registerCab("CAB-2", "HYD");
        portal.changeCabState("CAB-1", CabState.ON_TRIP);

        Booking booking = portal.bookCab("HYD");
        assertEquals("CAB-2", booking.getCabId(), "Only available cab should be booked");
    }

    @Test
    void shouldPreventCityChangeWhileOnTrip() {
        CabManagementPortal portal = new CabManagementPortal();

        portal.onboardCity("HYD");
        portal.onboardCity("BLR");
        portal.registerCab("CAB-1", "HYD");
        portal.bookCab("HYD");

        assertThrows(IllegalStateException.class, () -> portal.changeCabCity("CAB-1", "BLR"),
                "Changing city while on trip should fail");
    }

    @Test
    void shouldTrackCabHistory() {
        CabManagementPortal portal = new CabManagementPortal();

        portal.onboardCity("HYD");
        portal.registerCab("CAB-1", "HYD");

        portal.bookCab("HYD");
        pauseBriefly();
        portal.changeCabState("CAB-1", CabState.IDLE);

        List<CabHistoryEntry> history = portal.getCabHistory("CAB-1");
        assertEquals(3, history.size(), "History should include initial and transitioned states");
        assertEquals(CabState.IDLE, history.get(0).getState(), "Cab should begin in IDLE");
        assertEquals(CabState.ON_TRIP, history.get(1).getState(), "Booking should move cab to ON_TRIP");
        assertEquals(CabState.IDLE, history.get(2).getState(), "Returning from trip should move cab back to IDLE");
    }

    private void pauseBriefly() {
        try {
            Thread.sleep(5); // pause the current thread for about 5 milliseconds
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Test interrupted while waiting for clock progression", exception);
        }
    }
}
