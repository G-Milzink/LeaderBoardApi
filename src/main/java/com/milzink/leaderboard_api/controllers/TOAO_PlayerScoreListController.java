package com.milzink.leaderboard_api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.milzink.leaderboard_api.utillities.TOAO_PlayerDTO;
import com.milzink.leaderboard_api.utillities.TOAO_ScoreDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/toao/playerscores")
public class TOAO_PlayerScoreListController {

    Map<String, Integer> playerScoreList = new HashMap<>();

    @PostConstruct
    public void loadPlayerScores() {
        try {
            File file = new File(System.getProperty("user.dir") + "/data/TOAO_PlayerScoreList.json");
            if (file.exists()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                playerScoreList = objectMapper.readValue(file, new TypeReference<Map<String, Integer>>() {});
                System.out.println("Score list loaded from: " + file.getAbsolutePath());
            } else {
                System.out.println("No existing score list found. Starting fresh.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load player list: " + e.getMessage());
            playerScoreList = new HashMap<>(); // Initialize empty map as fallback
        }
    }

    @PostMapping("/store")
    public ResponseEntity<String> postScore(@RequestBody TOAO_ScoreDTO playerScore) {

        if (playerScore.getPlayerId() == null) {
            return ResponseEntity.badRequest().body("PlayerId can not be null.");
        }
        if (playerScore.getScore() == null) {
            return ResponseEntity.badRequest().body("Score can not be null.");
        }

        boolean isUpdate = playerScoreList.containsKey(playerScore.getPlayerId());
        playerScoreList.put(playerScore.getPlayerId(), playerScore.getScore());

        try {
            File dataFolder = new File(System.getProperty("user.dir") + "/data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            File file = new File(dataFolder, "TOAO_PlayerScoreList.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, playerScoreList);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save scores.");
        }

        if (isUpdate) {
            return ResponseEntity.ok("Player score updated successfully.");
        } else {
            return ResponseEntity.ok("Player score stored successfully.");
        }
    }

    @GetMapping("/retrieve/{playerId}")
    public ResponseEntity<?> getScore(@PathVariable("playerId") String playerId) {
        if (playerScoreList.containsKey(playerId)) {
            return ResponseEntity.ok(playerScoreList.get(playerId));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("playerId not found");
        }
    }

}
