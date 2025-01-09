package com.milzink.leaderboard_api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerListController {

    private ArrayList<String> playerIds = new ArrayList<>();

    // Method to reload player IDs from the scoreData.json file in the resources folder
    private void loadPlayerIds() {
        try {
            // Load the file as a resource from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/scoreData.json");

            if (inputStream != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Integer> scoreData = objectMapper.readValue(inputStream, new TypeReference<Map<String, Integer>>() {});
                playerIds = new ArrayList<>(scoreData.keySet());
                System.out.println("Player IDs reloaded successfully.");
            } else {
                System.out.println("No saved score data found, starting fresh.");
            }
        } catch (IOException e) {
            System.err.println("Failed to reload player IDs: " + e.getMessage());
        }
    }

    // Endpoint to check if a player ID exists
    @GetMapping("/exists/{playerId}")
    public ResponseEntity<?> doesPlayerExist(@PathVariable String playerId) {
        loadPlayerIds(); // Reload player IDs every time this endpoint is called
        if (playerIds.contains(playerId)) {
            return ResponseEntity.ok("Player ID exists.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player ID does not exist.");
        }
    }
}