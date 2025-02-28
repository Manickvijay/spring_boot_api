package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "block")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash")
    private String hash;

    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "nonce")
    private int nonce = 0;

    @Column(name = "previous_hash")
    private String previousHash;

    @ManyToOne
    @JoinColumn(name = "election_id", referencedColumnName = "id")
    private Election election;

    @ManyToOne
    @JoinColumn(name = "voter_id", referencedColumnName = "voter_id")
    private Voter voter;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @Column(name = "proof_of_work")
    private String proofOfWork;

    // Add ManyToOne relationship to Blockchain
    @ManyToOne
    @JsonBackReference
    private Blockchain blockchain;

    // Getters and Setters for blockchain
    public Blockchain getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }



    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                ", timestamp=" + timestamp +
                ", nonce=" + nonce +
                ", previousHash='" + previousHash + '\'' +
                ", election=" + election +
                ", voter=" + voter +
                ", candidate=" + candidate +
                ", proofOfWork='" + proofOfWork + '\'' +
                ", blockchain=" + blockchain +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }



    public String getProofOfWork() {
        return proofOfWork;
    }

    public void setProofOfWork(String proofOfWork) {
        this.proofOfWork = proofOfWork;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
