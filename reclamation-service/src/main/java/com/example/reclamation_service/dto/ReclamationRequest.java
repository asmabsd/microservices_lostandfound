package com.example.reclamation_service.dto;


public class ReclamationRequest {
    private String senderUserEmail;
    private String  targetUserEmail;
    private String reason;
    private String details;




    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSenderUserEmail() {
        return senderUserEmail;
    }

    public void setSenderUserEmail(String senderUserEmail) {
        this.senderUserEmail = senderUserEmail;
    }

    public String getTargetUserEmail() {
        return targetUserEmail;
    }

    public void setTargetUserEmail(String targetUserEmail) {
        this.targetUserEmail = targetUserEmail;
    }

    // Getters & Setters
}
