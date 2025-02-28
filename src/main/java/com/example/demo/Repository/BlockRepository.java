package com.example.demo.Repository;

import com.example.demo.Entity.Block;
import com.example.demo.Entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    List<Block> findByElection(Election election);
}
