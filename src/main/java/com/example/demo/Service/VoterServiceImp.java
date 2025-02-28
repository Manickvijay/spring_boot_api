package com.example.demo.Service;

import com.example.demo.DTO.VoterResponseDTO;
import com.example.demo.DTO.VoterSaveDTO;
import com.example.demo.Entity.Voter;
import com.example.demo.Repository.Voterrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoterServiceImp implements VoterService {

    public static final String IMAGE_STORAGE_PATH = "C:/path/to/your/images/voters/"; // **Correct this path!**

    @Autowired
    private Voterrepo voterrepo;

    @Override
    public Voter findByVoterId(String voterId) {
        return voterrepo.findByVoterId(voterId);
    }

    @Override
    public boolean verifyVoterCredentials(String votername, String voterId) {
        return voterrepo.existsByVoternameAndVoterId(votername, voterId);
    }

    @Override
    public Voter findByVoternameAndVoterId(String votername, String voterId) {
        return voterrepo.findByVoternameAndVoterId(votername, voterId);
    }

    @Override
    public String addVoter(VoterSaveDTO voterSaveDTO, MultipartFile imageFile) throws IOException {
        if (voterrepo.existsByMobileno(voterSaveDTO.getMobileno())) {
            return "Mobile number already exists!";
        }

        if (voterrepo.existsByEmail(voterSaveDTO.getEmail())) {
            return "Email already exists!";
        }

        String imageFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(IMAGE_STORAGE_PATH + imageFileName);

        Files.createDirectories(imagePath.getParent()); // Create directories if they don't exist
        imageFile.transferTo(imagePath);

        String voterId = "VOTER-" + UUID.randomUUID().toString().substring(0, 8);

        Voter voter = new Voter(
                voterSaveDTO.getVotername(),
                voterSaveDTO.getVoteraddress(),
                voterSaveDTO.getMobileno(),
                voterSaveDTO.getGender(),
                voterSaveDTO.getDateOfBirth(),
                voterSaveDTO.getEmail(),
                imageFileName
        );

        voter.setVoterId(voterId);
        voterrepo.save(voter);
        return "Voter added with ID: " + voterId;
    }

    @Override
    public String updateVoter(int id, VoterSaveDTO voterSaveDTO, MultipartFile imageFile) throws IOException {
        Voter voter = voterrepo.findById(id).orElse(null);
        if (voter == null) {
            return "Voter not found!";
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            // Delete old image if it exists
            if (voter.getImage() != null) {
                Path oldImagePath = Paths.get(IMAGE_STORAGE_PATH + voter.getImage());
                Files.deleteIfExists(oldImagePath);
            }

            String imageFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path imagePath = Paths.get(IMAGE_STORAGE_PATH + imageFileName);

            Files.createDirectories(imagePath.getParent());
            imageFile.transferTo(imagePath);

            voter.setImage(imageFileName);
        }

        voter.setVotername(voterSaveDTO.getVotername());
        voter.setVoteraddress(voterSaveDTO.getVoteraddress());
        voter.setMobileno(voterSaveDTO.getMobileno());
        voter.setGender(voterSaveDTO.getGender());
        voter.setDateOfBirth(voterSaveDTO.getDateOfBirth());
        voter.setEmail(voterSaveDTO.getEmail());

        voterrepo.save(voter);
        return "Voter updated successfully!";
    }

    @Override
    public String deleteVoter(int id) {
        Voter voter = voterrepo.findById(id).orElse(null);
        if (voter == null) {
            return "Voter not found!";
        }

        // Delete associated image file
        if (voter.getImage() != null) {
            try {
                Path imagePath = Paths.get(IMAGE_STORAGE_PATH + voter.getImage());
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                return "Error deleting image for voter: " + e.getMessage(); // Return error message
            }
        }

        voterrepo.deleteById(id);
        return "Voter deleted successfully!";
    }

    @Override
    public List<VoterSaveDTO> getAllVoters() {
        List<Voter> voters = voterrepo.findAll();
        List<VoterSaveDTO> voterDTOList = new ArrayList<>();

        for (Voter voter : voters) {
            VoterSaveDTO dto = new VoterSaveDTO(
                    voter.getId(),
                    voter.getVotername(),
                    voter.getVoteraddress(),
                    voter.getMobileno(),
                    voter.getGender(),
                    voter.getDateOfBirth(),
                    voter.getEmail(),
                    voter.getImage(),
                    voter.getVoterId()
            );
            voterDTOList.add(dto);
        }

        return voterDTOList;
    }

    @Override
    public VoterResponseDTO getVoterById(String voterId) {
        // Validate voter ID format (e.g., VOTER-5b395a89)
        if (!voterId.matches("^VOTER-[a-zA-Z0-9]{8}$")) {
            System.err.println("Invalid voter ID format: " + voterId);
            return new VoterResponseDTO("Invalid voter ID format", null, null);
        }

        // Search voter by voterId (string) instead of numeric ID
        Voter voter = voterrepo.findByVoterId(voterId);

        if (voter != null) {
            String imageUrl = IMAGE_STORAGE_PATH + voter.getImage(); // Get the image URL
            return new VoterResponseDTO("Voter found", voter.getVoterId(), imageUrl);
        } else {
            return new VoterResponseDTO("Voter not found", null, null);
        }
    }



}