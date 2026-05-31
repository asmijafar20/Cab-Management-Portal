package com.converzai.cabportal.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.converzai.cabportal.model.Booking;
import com.converzai.cabportal.model.Cab;
import com.converzai.cabportal.model.CabHistoryEntry;
import com.converzai.cabportal.model.CabSnapshot;
import com.converzai.cabportal.model.CabState;

public class CabManagementPortal {
    
    private final Map<String, Cab> cabs = new HashMap<>();
    private final Set<String> cities = new HashSet<>();
    private final Random random = new Random();

    public CabManagementPortal() {
    }

    public synchronized void onboardCity(String cityId) {
        validateId(cityId, "City id"); // just checking if the city is not blank
        cities.add(cityId); 
    }

    public synchronized void registerCab(String cabId, String cityId) {
        validateId(cabId, "Cab id"); // cab id shouldn't be blank
        ensureCityExists(cityId); 
        if (cabs.containsKey(cabId)) {
            throw new IllegalArgumentException("Cab already registered: " + cabId);
        }
        cabs.put(cabId, new Cab(cabId, cityId, now()));
    }

    public synchronized void changeCabCity(String cabId, String cityId) {
        ensureCityExists(cityId);
        Cab cab = getCab(cabId);
        if (cab.getState() == CabState.ON_TRIP) {
            throw new IllegalStateException("Cannot change city while cab is ON_TRIP");
        }
        cab.updateCity(cityId); // it is updating the city value 
    }

    public synchronized void changeCabState(String cabId, CabState newState) { 
        Cab cab = getCab(cabId);
        if (cab.getState() == newState) {
            return;
        }
        validateStateTransition(cab.getState(), newState); // checking if the state is valid to change
        cab.transitionTo(newState, now()); // change thev state of it 
    }

    public synchronized Booking bookCab(String cityId) {
        ensureCityExists(cityId);
        LocalDateTime bookingTime = now();

        // This part filters only available cabs
        List<Cab> eligibleCabs = cabs.values().stream()
                .filter(cab -> cab.getState() == CabState.IDLE)
                .filter(cab -> cityId.equals(cab.getCurrentCityId()))
                .toList();

        if (eligibleCabs.isEmpty()) {
            throw new IllegalStateException("No cab available in city: " + cityId);
        }

        // find the cab that has been idle the longest
        LocalDateTime earliestIdleStart = eligibleCabs.stream()
                .map(Cab::getCurrentIdleStartTime)
                .min(Comparator.naturalOrder())
                .orElseThrow();

        // handles the clash case by collecting all cabs tied for longest idle
        List<Cab> longestIdleCabs = eligibleCabs.stream()
                .filter(cab -> cab.getCurrentIdleStartTime().equals(earliestIdleStart))
                .toList();

        // the random assignment step
        Cab selectedCab = longestIdleCabs.get(random.nextInt(longestIdleCabs.size()));

        // the selected cab is immediately marked ON_TRIP
        selectedCab.transitionTo(CabState.ON_TRIP, bookingTime); 

        return new Booking(UUID.randomUUID().toString(), selectedCab.getCabId(), cityId, bookingTime);
    }

    public synchronized List<CabSnapshot> getCabSnapshots() {
        return cabs.values().stream()
                .map(cab -> new CabSnapshot(cab.getCabId(), cab.getState(), cab.getCurrentCityId()))
                .sorted(Comparator.comparing(CabSnapshot::getCabId))
                .toList(); // returning cab list
    }

    public synchronized List<CabHistoryEntry> getCabHistory(String cabId) {
        return getCab(cabId).getHistory(); //getCab(cabId) does validation and lookup before returning the history.
    }

    public synchronized Collection<String> getOnboardedCities() {
        return Set.copyOf(cities);
    }

    private void validateStateTransition(CabState currentState, CabState newState) {
        boolean valid = currentState == CabState.IDLE && newState == CabState.ON_TRIP
                || currentState == CabState.ON_TRIP && newState == CabState.IDLE;
        if (!valid) {
            throw new IllegalStateException("Invalid state transition: " + currentState + " -> " + newState);
        }
    }

    private Cab getCab(String cabId) {
        validateId(cabId, "Cab id");
        Cab cab = cabs.get(cabId);
        if (cab == null) {
            throw new IllegalArgumentException("Cab not found: " + cabId);
        }
        return cab;
    }

    private void ensureCityExists(String cityId) {
        validateId(cityId, "City id");
        if (!cities.contains(cityId)) {
            throw new IllegalArgumentException("City not onboarded: " + cityId);
        }
    }

    private void validateId(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " cannot be blank");
        }
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }
}
