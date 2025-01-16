package com.milzink.leaderboard_api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.milzink.leaderboard_api.utillities.TOAO_PlayerDTO;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class TOAO_PlayerListService {

    Map<String, TOAO_PlayerDTO> TOAO_PlayerList = new HashMap<>();

    public TOAO_PlayerListService() {
        System.out.println("TOAO_PLayerListService Loaded");
        loadPlayerList();
    }

    public void loadPlayerList() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/TOAO_PlayerList.json");

            if (inputStream != null) {
                TOAO_PlayerList = objectMapper.readValue(inputStream, new TypeReference<Map<String, TOAO_PlayerDTO>>() {});
                System.out.println("TOAO PlayerList loaded successfully");
            }
        } catch (IOException e) {
            System.out.println("Failed to load PlayerList: " + e.getMessage());
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

    private void savePlayerList() {
        try {
            File dataFolder = new File("/src/main/resources/data");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            File file = new File(dataFolder, "TOAO_PlayerList.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(file, TOAO_PlayerList);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load playerlist", e);
        }
    }

}
