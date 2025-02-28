package com.example.demo.Controller;

import com.example.demo.Entity.Block;
import com.example.demo.Service.BlockService;
import com.example.demo.Service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blocks")
public class BlockController {

    @Autowired
    private BlockService blockService;
    @Autowired
    private BlockchainService blockchainService;

    @PostMapping("/add-block")
    public ResponseEntity<?> addBlock(@RequestBody Block block) {
        try {
            blockService.addBlock(block);
            return ResponseEntity.ok("Block added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/{electionId}")
    public ResponseEntity<List<Block>> getBlocksByElectionId(@PathVariable Long electionId) {
        List<Block> blocks = blockchainService.getBlocksByElectionId(electionId);
        if (blocks == null || blocks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        }
        return ResponseEntity.ok(blocks);
    }

    @GetMapping("/block/{blockId}")
    public ResponseEntity<?> getBlockById(@PathVariable Long blockId) {
        try {
            Block block = blockService.getBlockById(blockId);
            return ResponseEntity.ok(block);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Block not found: " + e.getMessage());
        }
    }
}