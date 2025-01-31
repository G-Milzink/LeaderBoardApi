package com.milzink.leaderboard_api.services;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.milzink.leaderboard_api.utillities.TOAO_ScoreDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;

@Service
public class TOAO_ScoreListService {

    Map<String, TOAO_ScoreDTO> TOAO_ScoreList = new HashMap<>();

    @Value("${data.folder}")
    private String dataFolder;

    @PostConstruct
    public void init() {
        System.out.println("TOAO_ScoreListService started");
        loadScoreList();
    }

    public void loadScoreList() {
        try {
            File file = new File(dataFolder, "TOAO_ScoreList.json");
            if (file.exists()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                TOAO_ScoreList = objectMapper.readValue(file, new TypeReference<Map<String, TOAO_ScoreDTO>>() {});
                System.out.println("Score list loaded from: " + file.getAbsolutePath());
            } else {
                System.out.println("No existing score list found. Starting fresh.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load score list: " + e.getMessage());
            TOAO_ScoreList = new HashMap<>(); // Initialize empty map as fallback
        }
    }

    private void saveScoreList() {
        try {
            File file = new File(dataFolder, "TOAO_ScoreList.json");
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(file, TOAO_ScoreList);
            System.out.println("Score list saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save score list", e);
        }
    }

    public void addScore(String playerId, Integer score) {
        TOAO_ScoreDTO newScore = new TOAO_ScoreDTO();
        newScore.setScore(score);
        newScore.setTimestamp(LocalDateTime.now());
        TOAO_ScoreList.put(playerId, newScore);
        saveScoreList();
    }

    public int getScore(String playerId) {
           if(TOAO_ScoreList.containsKey(playerId)) {
               TOAO_ScoreDTO scoreDTO =  TOAO_ScoreList.get(playerId);
               return scoreDTO.getScore();
           }
           return 0;
    }

    public boolean deleteScore(String playerId) {

        if (TOAO_ScoreList.containsKey(playerId)) {
            TOAO_ScoreList.remove(playerId);
            saveScoreList();
            return true;
        }
        return false;
    }

    public Map<String, TOAO_ScoreDTO> getScoreList() {
        loadScoreList();
        return  TOAO_ScoreList;
    }
}
