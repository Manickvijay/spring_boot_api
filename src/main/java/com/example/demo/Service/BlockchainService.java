package com.example.demo.Service;

import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlockchainService {
    private final int DIFFICULTY = 2;
    private final String TARGET = new String(new char[DIFFICULTY]).replace('\0', '0');

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private ElectionRepo electionRepo;

    @Autowired
    private Voterrepo voterRepo;

    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private BlockchainRepo blockchainRepo;

    // Create a new blockchain for an election
    public Blockchain createBlockchain(Long electionId) {
        Election election = electionRepo.findById(electionId)
                .orElseThrow(() -> new RuntimeException("Election not found"));

        Blockchain blockchain = new Blockchain();
        blockchain.setElectionId(electionId);

        // Create genesis block
        Block genesis = new Block();
        genesis.setElection(election);
        genesis.setTimestamp(System.currentTimeMillis());
        genesis.setPreviousHash("0");
        genesis.setHash(calculateHash(genesis));

        blockchain.addBlock(genesis);
        blockchainRepo.save(blockchain);

        return blockchain;
    }

    // Get blockchain by election ID
    public Blockchain getBlockchain(Long electionId) {
        Blockchain blockchain = blockchainRepo.findByElectionId(electionId);
        if (blockchain == null) {
            throw new RuntimeException("Blockchain not found");
        }
        return blockchain;
    }


    // Mine a new block
    @Transactional
    public Block mineBlock(Long electionId, String voterId, Long candidateId) {
        Blockchain blockchain = getBlockchain(electionId);

        Voter voter = voterRepo.findByVoterId(voterId);
        if (voter == null) {
            throw new RuntimeException("Voter not found");
        }


        Candidate candidate = candidateRepo.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Block lastBlock = blockchain.getChain().get(blockchain.getChain().size() - 1);

        Block newBlock = new Block();
        newBlock.setElection(lastBlock.getElection());
        newBlock.setVoter(voter);
        newBlock.setCandidate(candidate);
        newBlock.setPreviousHash(lastBlock.getHash());
        newBlock.setTimestamp(System.currentTimeMillis());

        // Proof of work logic
        newBlock.setHash(proofOfWork(newBlock));
        blockchain.addBlock(newBlock);

        blockchainRepo.save(blockchain);
        return newBlock;
    }

    // Calculate block hash
    private String calculateHash(Block block) {
        String data = (block.getPreviousHash() != null ? block.getPreviousHash() : "")
                + block.getTimestamp()
                + block.getNonce()
                + (block.getVoter() != null ? block.getVoter().getVoterId() : "")
                + (block.getCandidate() != null ? block.getCandidate().getId() : "")
                + (block.getElection() != null ? block.getElection().getId() : "");
        return DigestUtils.sha256Hex(data);
    }

    // Proof-of-work implementation
    private String proofOfWork(Block block) {
        System.out.println("Starting proof of work for block: " + block);
        block.setHash(calculateHash(block));

        int attempt = 0;
        while (!block.getHash().substring(0, DIFFICULTY).equals(TARGET)) {
            block.setNonce(block.getNonce() + 1);
            block.setHash(calculateHash(block));

            if (attempt % 1000 == 0) {
                System.out.println("Attempt " + attempt + ": " + block.getHash());
            }
            attempt++;
        }

        System.out.println("Proof of work completed. Hash: " + block.getHash());
        return block.getHash();
    }

    // Validate blockchain integrity
    public boolean validateChain(Long electionId) {
        Blockchain blockchain = getBlockchain(electionId);
        List<Block> chain = blockchain.getChain();

        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getHash().equals(calculateHash(currentBlock))) {
                return false;
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    // Get blocks by election ID
    public List<Block> getBlocksByElectionId(Long electionId) {
        Blockchain blockchain = getBlockchain(electionId);
        return blockchain.getChain();
    }




}
