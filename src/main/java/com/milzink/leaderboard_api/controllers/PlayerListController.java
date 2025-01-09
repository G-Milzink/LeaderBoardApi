package com.milzink.leaderboard_api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milzink.leaderboard_api.utillities.PlayerDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/player-details")
public class PlayerListController {

   Map<String, PlayerDetails> playerDetailsList = new HashMap<>();

   @PostConstruct
   public void loadPlayerDetails() {
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/playerList.json");

           if (inputStream != null) {
               playerDetailsList = objectMapper.readValue(inputStream, new TypeReference<Map<String, PlayerDetails>>() {});
               System.out.println("PlayerList loaded succesfully");
           } else {
               System.out.println("PlayerList is empty");
           }
       } catch (IOException e) {
           System.out.println("Failed to load PlayerList" + e.getMessage());
       }
   }




}