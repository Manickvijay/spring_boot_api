package com.example.demo.Repository;

import com.example.demo.Entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface Voterrepo extends JpaRepository<Voter, Integer> {
    boolean existsByMobileno(long mobileno);  // Changed from int to long
    boolean existsByEmail(String email);
    Optional<Voter> findById(Integer id);
    boolean existsByVoternameAndVoterId(String votername, String voterId);
    Voter findByVoternameAndVoterId(String votername, String voterId);
    Voter findByVoterId(String voterId);

}