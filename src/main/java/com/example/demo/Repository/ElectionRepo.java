package com.example.demo.Repository;

import com.example.demo.Entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepo extends JpaRepository<Election, Long> {

}