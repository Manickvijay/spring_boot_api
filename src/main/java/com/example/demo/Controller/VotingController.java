package com.example.demo.Controller;

import com.example.demo.DTO.VoteRequest;
import com.example.demo.Service.BlockchainService;
import com.example.demo.Service.VoteService;
import com.example.demo.Service.SmartContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VotingController {
    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private SmartContractService smartContractService;

    @Autowired
    private VoteService voteService;

    // BlockController.java
    @GetMapping("/validate/{electionId}")
    public ResponseEntity<String> validateChain(@PathVariable Long electionId) {
        boolean isValid = blockchainService.validateChain(electionId);
        return isValid ?
                ResponseEntity.ok("Blockchain valid") :
                ResponseEntity.status(HttpStatus.CONFLICT).body("Blockchain invalid");
    }

    // New Endpoint to fetch blocks for a specific election





}