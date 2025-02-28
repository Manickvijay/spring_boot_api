package com.example.demo.DTO;

public class VoterSaveDTO {

    private int id;
    private String votername;
    private String voteraddress;
    private long mobileno;  // Changed from int to long
    private String gender;
    private String dateOfBirth;
    private String email;
    private String image;
    private String voterId;

    // Constructor with all parameters
    public VoterSaveDTO(int id, String votername, String voteraddress, long mobileno,
                        String gender, String dateOfBirth, String email, String image, String voterId) {
        this.id = id;
        this.votername = votername;
        this.voteraddress = voteraddress;
        this.mobileno = mobileno;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.image = image;
        this.voterId = voterId;
    }

    // Default constructor
    public VoterSaveDTO() {
    }

    // Constructor without the image parameter (used when saving a new voter)
    public VoterSaveDTO(String votername, String voteraddress, long mobileno, String gender,
                        String dateOfBirth, String email, String image) {
        this.votername = votername;
        this.voteraddress = voteraddress;
        this.mobileno = mobileno;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.image = image;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getVotername() { return votername; }
    public void setVotername(String votername) { this.votername = votername; }
    public String getVoteraddress() { return voteraddress; }
    public void setVoteraddress(String voteraddress) { this.voteraddress = voteraddress; }
    public long getMobileno() { return mobileno; }  // Changed from int to long
    public void setMobileno(long mobileno) { this.mobileno = mobileno; }  // Changed from int to long
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getVoterId() { return voterId; }
    public void setVoterId(String voterId) { this.voterId = voterId; }


}