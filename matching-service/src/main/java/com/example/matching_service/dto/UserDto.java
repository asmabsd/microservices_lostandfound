package com.example.matching_service.dto;

public class UserDto {
    private Long id;
    private boolean premium;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isPremium() { return premium; }
    public void setPremium(boolean premium) { this.premium = premium; }
}
