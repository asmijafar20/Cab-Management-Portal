package com.converzai.cabportal.api;

public class CityRequest {
    private String cityId;

    public CityRequest() {
    }

    public CityRequest(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
