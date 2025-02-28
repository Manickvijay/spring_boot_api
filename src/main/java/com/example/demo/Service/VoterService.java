package com.example.demo.Service;

import com.example.demo.DTO.VoterResponseDTO;
import com.example.demo.DTO.VoterSaveDTO;
import com.example.demo.Entity.Voter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VoterService {
    String addVoter(VoterSaveDTO voterSaveDTO, MultipartFile imageFile) throws IOException;
    String updateVoter(int id, VoterSaveDTO voterSaveDTO, MultipartFile imageFile) throws IOException; // Keep this method
    String deleteVoter(int id);
    List<VoterSaveDTO> getAllVoters();
    boolean verifyVoterCredentials(String votername, String voterId);
    Voter findByVoternameAndVoterId(String votername, String voterId);
    Voter findByVoterId(String voterId);
    VoterResponseDTO getVoterById(String voterId);

}