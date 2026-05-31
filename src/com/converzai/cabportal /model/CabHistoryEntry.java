package com.converzai.cabportal.model;

import java.time.LocalDateTime;

public class CabHistoryEntry {
    private final CabState state;
    private final LocalDateTime changedAt;

    public CabHistoryEntry(CabState state, LocalDateTime changedAt) {
        this.state = state;
        this.changedAt = changedAt;
    }

    public CabState getState() {
        return state;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}
