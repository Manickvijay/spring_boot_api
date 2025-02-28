package com.example.demo.Service;


import com.example.demo.DTO.VoteRequest;
import com.example.demo.Entity.Candidate;
import com.example.demo.Entity.Election;
import com.example.demo.Entity.Voter;
import com.example.demo.Repository.CandidateRepo;
import com.example.demo.Repository.ElectionRepo;
import com.example.demo.Repository.Voterrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// VoteServiceImpl.java
@Service
public class VoteServiceImpl implements VoteService {
    @Autowired private BlockchainService blockchainService;
    @Autowired private Voterrepo voterRepo;
    @Autowired private CandidateRepo candidateRepo;

    public void castVote(VoteRequest voteRequest) {
        // Validate entities
        Voter voter = voterRepo.findByVoterId(voteRequest.getVoterId());
        if (voter == null) {
            throw new IllegalArgumentException("Invalid voter ID");
        }

        Candidate candidate = candidateRepo.findById(voteRequest.getCandidateId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid candidate"));

        // Mine block
        blockchainService.mineBlock(
                voteRequest.getElectionId(),
                voteRequest.getVoterId(),
                voteRequest.getCandidateId()
        );
    }
}