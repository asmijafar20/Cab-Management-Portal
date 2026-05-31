package com.converzai.cabportal.api;

import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.converzai.cabportal.model.Booking;
import com.converzai.cabportal.model.CabHistoryEntry;
import com.converzai.cabportal.model.CabSnapshot;
import com.converzai.cabportal.service.CabManagementPortal;

@RestController
@RequestMapping("/api")
public class CabPortalController {
    
    private final CabManagementPortal portal;

    public CabPortalController(CabManagementPortal portal) {
        this.portal = portal;
    }

    @PostMapping("/cities")
    public MessageResponse onboardCity(@RequestBody CityRequest request) {
        portal.onboardCity(request.getCityId());
        return new MessageResponse("City onboarded");
    }

    @GetMapping("/cities")
    public Collection<String> getCities() {
        return portal.getOnboardedCities();
    }

    @PostMapping("/cabs")
    public MessageResponse registerCab(@RequestBody RegisterCabRequest request) {
        portal.registerCab(request.getCabId(), request.getCityId());
        return new MessageResponse("Cab registered");
    }

    @PutMapping("/cabs/{cabId}/city")
    public MessageResponse changeCabCity(@PathVariable String cabId, @RequestBody CityRequest request) {
        portal.changeCabCity(cabId, request.getCityId());
        return new MessageResponse("Cab city updated");
    }

    @PutMapping("/cabs/{cabId}/state")
    public MessageResponse changeCabState(@PathVariable String cabId, @RequestBody ChangeCabStateRequest request) {
        portal.changeCabState(cabId, request.getState());
        return new MessageResponse("Cab state updated");
    }

    @GetMapping("/cabs")
    public List<CabSnapshot> getCabSnapshots() {
        return portal.getCabSnapshots();
    }

    @GetMapping("/cabs/{cabId}/history")
    public List<CabHistoryEntry> getCabHistory(@PathVariable String cabId) {
        return portal.getCabHistory(cabId);
    }

    @PostMapping("/bookings")
    public Booking bookCab(@RequestBody CityRequest request) {
        return portal.bookCab(request.getCityId());
    }
}
