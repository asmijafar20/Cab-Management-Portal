package com.converzai.cabportal.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cab {
    private final String cabId;
    private CabState state;
    private String currentCityId;
    private LocalDateTime currentIdleStartTime;
    private final List<CabHistoryEntry> history;

    public Cab(String cabId, String currentCityId, LocalDateTime createdAt) {
        this.cabId = cabId;
        this.state = CabState.IDLE;
        this.currentCityId = currentCityId;
        this.currentIdleStartTime = createdAt;
        this.history = new ArrayList<>();
        this.history.add(new CabHistoryEntry(CabState.IDLE, createdAt));
    }

    public String getCabId() {
        return cabId;
    }

    public CabState getState() {
        return state;
    }

    public String getCurrentCityId() {
        return currentCityId;
    }

    public LocalDateTime getCurrentIdleStartTime() {
        return currentIdleStartTime;
    }

    public List<CabHistoryEntry> getHistory() {
        return Collections.unmodifiableList(history); // users can see the list but they cannot modify it So this is a small encapsulation/safety measure.

    }

    public void updateCity(String cityId) {
        this.currentCityId = cityId;
    }

    public void transitionTo(CabState newState, LocalDateTime changedAt) {
        this.state = newState;
        if (newState == CabState.IDLE) {
            this.currentIdleStartTime = changedAt;
        } else {
            this.currentIdleStartTime = null;
            this.currentCityId = null;
        }
        this.history.add(new CabHistoryEntry(newState, changedAt));
    }
}
