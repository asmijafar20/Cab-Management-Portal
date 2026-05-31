package com.converzai.cabportal.api;

import com.converzai.cabportal.model.CabState;

public class ChangeCabStateRequest {
    private CabState state;

    public ChangeCabStateRequest() {
    }

    public ChangeCabStateRequest(CabState state) {
        this.state = state;
    }

    public CabState getState() {
        return state;
    }

    public void setState(CabState state) {
        this.state = state;
    }
}
