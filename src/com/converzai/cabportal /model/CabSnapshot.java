package com.converzai.cabportal.model;

public class CabSnapshot {
    private final String cabId;
    private final CabState cabState;
    private final String cityId;

    public CabSnapshot(String cabId, CabState cabState, String cityId) {
        this.cabId = cabId;
        this.cabState = cabState;
        this.cityId = cityId;
    }

    public String getCabId() {
        return cabId;
    }

    public CabState getCabState() {
        return cabState;
    }

    public String getCityId() {
        return cityId;
    }
}
