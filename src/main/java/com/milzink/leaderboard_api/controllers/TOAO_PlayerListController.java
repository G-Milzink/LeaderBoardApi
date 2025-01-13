package com.milzink.leaderboard_api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milzink.leaderboard_api.utillities.TOAO_PlayerDTO;
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
@RequestMapping("/playerlist")
public class TOAO_PlayerListController {

   Map<String, TOAO_PlayerDTO> playerDetailsList = new HashMap<>();

   @PostConstruct
   public void loadPlayerDetails() {
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/playerList.json");

           if (inputStream != null) {
               playerDetailsList = objectMapper.readValue(inputStream, new TypeReference<Map<String, TOAO_PlayerDTO>>() {});
               System.out.println("TOAO PlayerList loaded successfully");
           }
       } catch (IOException e) {
           System.out.println("Failed to load PlayerList: " + e.getMessage());
       }
   }

   @PostMapping("/store")
   public ResponseEntity<String> addPlayer(@RequestBody Map<String, String> newPlayer) {

       if (newPlayer.get("playerId").isBlank()) {
           return ResponseEntity.badRequest().body("PlayerId field can not be blank.");
       }
       if (newPlayer.get("password").isBlank()) {
           return ResponseEntity.badRequest().body("Password field can not be blank.");
       }
       if (newPlayer.get("email").isBlank()) {
           return ResponseEntity.badRequest().body("Email field can not be blank.");
       }

       if (playerDetailsList.containsKey(newPlayer.get("playerId"))) {
           return ResponseEntity.badRequest().body("PlayerId must be unique");
       }

       String playerId = newPlayer.get("playerId");
       String password = newPlayer.get("password");
       String email = newPlayer.get("email");
       TOAO_PlayerDTO TOOAYPlayerDTO = new TOAO_PlayerDTO(password,email);
       playerDetailsList.put(playerId, TOOAYPlayerDTO);

       try {
           // Get the path to the "data" folder inside the main package
           File dataFolder = new File("src/main/resources/data");
           if (!dataFolder.exists()) {
               dataFolder.mkdirs(); // Create the folder if it doesn't exist
           }

           // Write the JSON file inside the "data" folder
           File file = new File(dataFolder, "playerList.json");
           ObjectMapper objectMapper = new ObjectMapper();
           objectMapper.writeValue(file, playerDetailsList);

       } catch (IOException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save PlayerList.");
       }

       return ResponseEntity.ok("New player stored successfully");
   }
}