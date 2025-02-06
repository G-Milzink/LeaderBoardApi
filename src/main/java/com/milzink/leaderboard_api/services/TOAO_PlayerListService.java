package com.milzink.leaderboard_api.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.milzink.leaderboard_api.utillities.TOAO_PlayerDTO;

@Service
public class TOAO_PlayerListService {

    Map<String, TOAO_PlayerDTO> TOAO_PlayerList = new HashMap<>();

    @Value("${data.folder}")
    private String dataFolder;

    @PostConstruct
    public void init() {
        System.out.println("TOAO_PLayerListService started");
        loadPlayerList();
    }

    public void loadPlayerList() {
        try {
            File file = new File(dataFolder, "TOAO_PlayerList.json");
            if (file.exists()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                TOAO_PlayerList = objectMapper.readValue(file, new TypeReference<Map<String, TOAO_PlayerDTO>>() {});
                System.out.println("Player list loaded from: " + file.getAbsolutePath());
            } else {
                System.out.println("No existing player list found. Starting fresh.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load player list: " + e.getMessage());
            TOAO_PlayerList = new HashMap<>(); // Initialize empty map as fallback
        }
    }

    private void savePlayerList() {
        try {
            File file = new File(dataFolder, "TOAO_PlayerList.json");
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs(); // Create directory if it doesn't exist
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(file, TOAO_PlayerList);
            System.out.println("Player list saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save player list", e);
        }
    }

    public void addPlayer(String playerId, String password, String email) {

        if (TOAO_PlayerList.containsKey(playerId)) {
            throw new IllegalArgumentException("PlayerId already exists");
        }

        TOAO_PlayerDTO newPlayer = new TOAO_PlayerDTO(password, email);
        TOAO_PlayerList.put(playerId, newPlayer);

        savePlayerList();
    }

    public boolean getPlayer(String playerId, String password) {

        return TOAO_PlayerList.containsKey(playerId) &&
                TOAO_PlayerList.get(playerId).getPassword().equals(password);

    }

    public boolean deletePlayer(String playerId, String password) {

        if (TOAO_PlayerList.containsKey(playerId) &&
                TOAO_PlayerList.get(playerId).getPassword().equals(password)) {

            TOAO_PlayerList.remove(playerId);
            savePlayerList();
            return true;
        }
        return false;
    }

}
