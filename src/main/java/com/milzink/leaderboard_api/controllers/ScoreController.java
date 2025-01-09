package com.milzink.leaderboard_api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milzink.leaderboard_api.utillities.ScoreDTO;
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
@RequestMapping("/score")
public class ScoreController {

    Map<String, Integer> scoreStorage = new HashMap<>();

    @PostConstruct
    public void loadScoreData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Load the file from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/scoreData.json");

            if (inputStream != null) {
                scoreStorage = objectMapper.readValue(inputStream, new TypeReference<Map<String, Integer>>() {});
                System.out.println("Scores loaded successfully.");
            } else {
                System.out.println("No saved scores found, starting fresh.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load scores: " + e.getMessage());
        }
    }

    @PostMapping("/store")
    public ResponseEntity<String> postScore(@RequestBody ScoreDTO playerScore) {

        if (playerScore.getPlayerId() == null || playerScore.getScore() == null) {
            return ResponseEntity.badRequest().body("playerId or score cannot be null.");
        }

        boolean isUpdate = scoreStorage.containsKey(playerScore.getPlayerId());
        scoreStorage.put(playerScore.getPlayerId(), playerScore.getScore());

        try {
            // Get the path to the "data" folder inside the main package
            File dataFolder = new File("src/main/resources/data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs(); // Create the folder if it doesn't exist
            }

            // Write the JSON file inside the "data" folder
            File file = new File(dataFolder, "scoreData.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, scoreStorage);

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
        if (scoreStorage.containsKey(playerId)) {
            return ResponseEntity.ok(scoreStorage.get(playerId));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("playerId not found");
        }
    }

}
