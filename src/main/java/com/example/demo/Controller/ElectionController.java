package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import com.example.demo.Service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/election")
public class ElectionController {

    @Autowired
    private Voterrepo voterRepo;

    @Autowired
    private ElectionRepo electionRepo;

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private BlockRepository blockRepo;

    @Autowired
    private BlockchainRepo blockchainRepo;
    @Autowired
    private CandidateRepo candidateRepo;

    @GetMapping("/all")
    public List<Election> getAllElections() {
        return electionRepo.findAll();
    }

    // ElectionController.java
    // ElectionController.java
    @PostMapping("/save")
    public ResponseEntity<String> saveElection(@RequestBody Election election) {
        Election savedElection = electionRepo.save(election);

        // Create blockchain with genesis block
        blockchainService.createBlockchain(savedElection.getId());

        return ResponseEntity.ok("Election saved with blockchain ID: " + savedElection.getId());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateElection(@PathVariable Long id, @RequestBody Election election) {
        election.setId(id);
        electionRepo.save(election);
        return ResponseEntity.ok("Election updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteElection(@PathVariable Long id) {
        try {
            Election election = electionRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Election not found"));

            // 1. Delete all blocks related to this election
            List<Block> blocks = blockRepo.findByElection(election); // Ensure this method exists
            blockRepo.deleteAll(blocks);

            // 2. Delete all candidates in the election
            List<Candidate> candidates = election.getCandidates();
            candidateRepo.deleteAll(candidates);

            // 3. Delete the blockchain linked to the election
            Blockchain blockchain = blockchainRepo.findByElectionId(id);
            if (blockchain != null) {
                blockchainRepo.delete(blockchain);
            }

            // 4. Finally, delete the election
            electionRepo.delete(election);

            return ResponseEntity.ok("Election deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting election: " + e.getMessage());
        }
    }
    @PostMapping("/candidate/save")
    public ResponseEntity<String> addCandidate(@RequestBody Map<String, Object> request) {
        try {
            Integer electionId = (Integer) request.get("electionId");
            Integer voterId = (Integer) request.get("voterId");
            String voterName = (String) request.get("voterName");
            String voterAddress = (String) request.get("voterAddress");
            String voterImage = (String) request.get("voterImage");

            Election election = electionRepo.findById(Long.valueOf(electionId))
                    .orElseThrow(() -> new RuntimeException("Election not found"));
            Voter voter = voterRepo.findById(voterId)
                    .orElseThrow(() -> new RuntimeException("Voter not found"));

            Candidate candidate = new Candidate();
            candidate.setElection(election);
            candidate.setVoter(voter);
            candidate.setVoterName(voterName);
            candidate.setVoterAddress(voterAddress);
            candidate.setVoterImage(voterImage);
            System.out.println("Request Payload: " + request);
            candidateRepo.save(candidate);
            return ResponseEntity.ok("Candidate added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    @DeleteMapping("/candidate/delete/{candidateId}")
    public ResponseEntity<String> removeCandidate(@PathVariable long candidateId) {
        candidateRepo.deleteById(candidateId);
        return ResponseEntity.ok("Candidate removed successfully");
    }

}