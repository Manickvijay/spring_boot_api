
package com.example.demo.Service;

import com.example.demo.Entity.Block;
import com.example.demo.Repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockRepository blockRepository;

    @Override
    public void addBlock(Block block) {
        blockRepository.save(block);
    }

    @Override
    public List<Block> getBlocksByElectionId(Long electionId) {
        return blockRepository.findAll().stream()
                .filter(block -> block.getElection() != null && block.getElection().getId().equals(electionId))
                .toList();
    }


    @Override
    public Block getBlockById(Long blockId) {
        return blockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Block not found with ID: " + blockId));
    }
}
