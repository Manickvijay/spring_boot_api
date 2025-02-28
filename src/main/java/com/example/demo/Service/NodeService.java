package com.example.demo.Service;


import com.example.demo.Entity.Block;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class NodeService {

    @Value("${node.addresses}")
    private List<String> nodeAddresses; // List of node addresses

    private final RestTemplate restTemplate;

    public NodeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Broadcast a new block to all nodes
    public void broadcastBlock(Block block) {
        for (String node : nodeAddresses) {
            restTemplate.postForEntity(node + "/api/node/receive-block", block, String.class);
        }
    }
}