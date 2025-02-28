package com.example.demo.DTO;

public class VoterResponseDTO {
    private String message;
    private String voterId; // Add this field
    private String imageUrl; // Rename from 'image' to 'imageUrl'

    // Proper constructor
    public VoterResponseDTO(String message, String voterId, String imageUrl) {
        this.message = message;
        this.voterId = voterId;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}