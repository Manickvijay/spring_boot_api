package com.example.demo.Service;

import com.example.demo.Entity.Block;
import java.util.List;

public interface BlockService {
    void addBlock(Block block);
    List<Block> getBlocksByElectionId(Long electionId);
    Block getBlockById(Long blockId);
}