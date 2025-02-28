package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "voterlist")
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sl_no", length = 50)
    private int id;

    @Column(name = "voter_name", length = 50)
    private String votername;

    @Column(name = "voter_address", length = 60)
    private String voteraddress;

    @Column(name = "voter_id", unique = true)
    private String voterId;

    @Column(name = "mobile", length = 12, unique = true)
    private long mobileno;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "dob")
    private String dateOfBirth;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "voter_image")
    private String image;

    public Voter(String votername, String voteraddress, long mobileno, String gender, String dateOfBirth, String email, String image) {
        this.votername = votername;
        this.voteraddress = voteraddress;
        this.mobileno = mobileno;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.image = image;
    }

    public Voter() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVotername() {
        return votername;
    }

    public void setVotername(String votername) {
        this.votername = votername;
    }

    public String getVoteraddress() {
        return voteraddress;
    }

    public void setVoteraddress(String voteraddress) {
        this.voteraddress = voteraddress;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public long getMobileno() {
        return mobileno;
    }

    public void setMobileno(long mobileno) {
        this.mobileno = mobileno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}