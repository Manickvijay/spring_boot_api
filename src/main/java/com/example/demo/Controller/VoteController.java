package com.example.demo.Controller;


import com.example.demo.DTO.VoteRequest;
import com.example.demo.Service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/cast-vote")
    public ResponseEntity<?> castVote(@RequestBody VoteRequest voteRequest) {
        try {
            voteService.castVote(voteRequest);
            return ResponseEntity.ok("Vote cast successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error casting vote: " + e.getMessage());
        }
    }
}
