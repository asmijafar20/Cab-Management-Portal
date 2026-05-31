package com.converzai.cabportal.api;

public class RegisterCabRequest {
    private String cabId;
    private String cityId;

    public RegisterCabRequest() {
    }

    public RegisterCabRequest(String cabId, String cityId) {
        this.cabId = cabId;
        this.cityId = cityId;
    }

    public String getCabId() {
        return cabId;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
