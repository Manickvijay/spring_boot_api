package com.example.demo.Repository;


import com.example.demo.Entity.Block;
import com.example.demo.Entity.Blockchain;
import com.example.demo.Entity.Election;
import com.example.demo.Entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockchainRepo extends JpaRepository<Blockchain, Long> {
    Blockchain findByElectionId(Long electionId);

    // This must exist
}