package com.example.demo.Entity;

import com.example.demo.Entity.Block;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "blockchain")
public class Blockchain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long electionId;

    @OneToMany(mappedBy = "blockchain", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Block> chain = new ArrayList<>();

    public void addBlock(Block block) {
        block.setBlockchain(this); // Maintain bidirectional relationship
        this.chain.add(block);
    }

    public Blockchain() {
        this.chain = new ArrayList<>();  // Initialize chain to avoid null
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }

    public List<Block> getChain() {
        return chain;
    }

    public void setChain(List<Block> chain) {
        this.chain = chain;
    }
    // In Blockchain.java
    public Block getLastBlock() {
        if (chain.isEmpty()) {
            throw new IllegalStateException("No blocks in chain");
        }
        return chain.get(chain.size() - 1);
    }



}