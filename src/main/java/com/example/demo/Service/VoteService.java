package com.example.demo.Service;


import com.example.demo.DTO.VoteRequest;

public interface VoteService {
    void castVote(VoteRequest voteRequest);
}
