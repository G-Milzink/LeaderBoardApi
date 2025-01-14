package com.milzink.leaderboard_api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/playerscores")
public class TOAO_PlayerScoreListController {

    Map<String, Integer> playerScoreList = new HashMap<>();

    @PostConstruct
    public void loadPlayerScores() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/playerScoreList.json");

            if (inputStream != null) {
                playerScoreList = objectMapper.readValue(inputStream, new TypeReference<Map<String, Integer>>() {});
                System.out.println("TOAO ScoreList loaded successfully.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load ScoreList: " + e.getMessage());
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
            File dataFolder = new File("src/main/resources/data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            File file = new File(dataFolder, "playerScoreList.json");
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
