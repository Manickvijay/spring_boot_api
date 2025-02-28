package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voter_id", referencedColumnName = "voter_id")
    private Voter voter;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @Column(name = "voter_name")
    private String voterName;

    @Column(name = "voter_address")
    private String voterAddress;

    @Column(name = "voter_image")
    private String voterImage;

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }

    public String getVoterAddress() {
        return voterAddress;
    }

    public void setVoterAddress(String voterAddress) {
        this.voterAddress = voterAddress;
    }

    public String getVoterImage() {
        return voterImage;
    }

    public void setVoterImage(String voterImage) {
        this.voterImage = voterImage;
    }

    public void setVoterId(String voterId) {
    }

}