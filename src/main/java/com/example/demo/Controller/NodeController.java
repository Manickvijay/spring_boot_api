package com.example.demo.Controller;

import com.example.demo.Entity.Block;
import com.example.demo.Service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/node")
public class NodeController {
    @Autowired
    private BlockchainService blockchainService;

    @PostMapping("/receive-block")
    public ResponseEntity<String> receiveBlock(@RequestBody Block block) {
        Long electionId = block.getElection() != null ? block.getElection().getId() : null;

        if (electionId == null) {
            return ResponseEntity.badRequest().body("Election ID is missing in the block");
        }

        blockchainService.mineBlock(electionId, block.getVoter().getVoterId(), block.getCandidate().getId());
        return ResponseEntity.ok("Block received and added to the chain for election: " + electionId);
    }


}