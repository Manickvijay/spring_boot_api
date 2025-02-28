package com.example.demo.Service;

import com.example.demo.Entity.Block;
import com.example.demo.Entity.Blockchain;
import com.example.demo.Repository.Voterrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartContractService {
    @Autowired
    private Voterrepo voterRepo;

    @Autowired
    private BlockchainService blockchainService;

    // Validate voter eligibility for a specific election
    public boolean validateVote(String voterId, Long electionId) {
        if (!voterId.matches("^VOTER-[a-zA-Z0-9]{8}$")) {
            System.out.println("Invalid voter ID format: " + voterId);
            return false;
        }

        // Check if voter has already voted in this election
        Blockchain blockchain = blockchainService.getBlockchain(electionId);
        if (blockchain == null) {
            blockchain = blockchainService.createBlockchain(electionId);
        }

        return blockchain.getChain().stream()
                .noneMatch(block -> block.getVoter() != null && block.getVoter().getVoterId().equals(voterId));
    }
}