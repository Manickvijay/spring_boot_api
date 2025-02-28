package com.example.demo.Controller;

import com.example.demo.DTO.VoterResponseDTO;
import com.example.demo.DTO.VoterSaveDTO;
import com.example.demo.Entity.Voter;
import com.example.demo.Service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


import static com.example.demo.Service.VoterServiceImp.IMAGE_STORAGE_PATH;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/voter")
public class VoterController {

    // VoterController.java
    @Autowired
    private VoterService voterservice;  // Make sure variable name matches
    // Add this dependency in your controller



    @PostMapping("/save")
    public ResponseEntity<String> saveVoter(@RequestParam("votername") String votername,
                                            @RequestParam("voteraddress") String voteraddress,
                                            @RequestParam("mobileno") long mobileno,  // Changed from int to long
                                            @RequestParam("gender") String gender,
                                            @RequestParam("dateOfBirth") String dateOfBirth,
                                            @RequestParam("email") String email,
                                            @RequestParam("image") MultipartFile image) {
        try {
            VoterSaveDTO voterSaveDTO = new VoterSaveDTO(votername, voteraddress, mobileno, gender, dateOfBirth, email, null);
            String result = voterservice.addVoter(voterSaveDTO, image);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving voter: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVoter(
            @PathVariable int id,
            @RequestParam("votername") String votername,
            @RequestParam("voteraddress") String voteraddress,
            @RequestParam("mobileno") long mobileno,  // Changed from String to long
            @RequestParam("gender") String gender,
            @RequestParam("dateOfBirth") String dateOfBirth,
            @RequestParam("email") String email,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            VoterSaveDTO voterSaveDTO = new VoterSaveDTO(id, votername, voteraddress, mobileno, gender, dateOfBirth, email, null, null);
            String result = voterservice.updateVoter(id, voterSaveDTO, imageFile);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating voter: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVoter(@PathVariable int id) {
        String result = voterservice.deleteVoter(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VoterSaveDTO>> getAllVoters() {
        List<VoterSaveDTO> voters = voterservice.getAllVoters();
        return ResponseEntity.ok(voters);
    }
    @GetMapping("/image/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get(IMAGE_STORAGE_PATH + imageName);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
    @PostMapping("/voter-login")
    public ResponseEntity<VoterResponseDTO> voterLogin(
            @RequestParam String votername,
            @RequestParam String voterId) {

        Voter voter = voterservice.findByVoternameAndVoterId(votername, voterId);

        if (voter != null) {
            // Ensure image URL is correctly formed
            String imageUrl = (voter.getImage() != null && !voter.getImage().isEmpty())
                    ? "http://localhost:8085/api/v1/voter/image/" + voter.getImage()
                    : null;

            // Debug Log
            System.out.println("Voter Image from DB: " + voter.getImage());
            System.out.println("Generated Image URL: " + imageUrl);

            // Return response with voterId and imageUrl
            return ResponseEntity.ok(new VoterResponseDTO(
                    "Login successful",
                    voter.getVoterId(),
                    imageUrl
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new VoterResponseDTO(
                            "Invalid voter name or voter ID",
                            null,
                            null
                    ));
        }
    }



    @GetMapping("/{voterId}")
    public ResponseEntity<VoterResponseDTO> getVoterById(@PathVariable String voterId) {
        VoterResponseDTO voterResponseDTO = voterservice.getVoterById(voterId);
        if (voterResponseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(voterResponseDTO);
    }


}


