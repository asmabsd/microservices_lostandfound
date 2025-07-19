package com.example.matching_service.entities;

import java.util.List;

public class MatchingResponse {
    private Long reference_id;
    private List<Item> matches;
    private int match_count;
    private boolean success;

    public Long getReference_id() {
        return reference_id;
    }

    public void setReference_id(Long reference_id) {
        this.reference_id = reference_id;
    }

    public List<Item> getMatches() {
        return matches;
    }

    public void setMatches(List<Item> matches) {
        this.matches = matches;
    }

    public int getMatch_count() {
        return match_count;
    }

    public void setMatch_count(int match_count) {
        this.match_count = match_count;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // getters et setters
}