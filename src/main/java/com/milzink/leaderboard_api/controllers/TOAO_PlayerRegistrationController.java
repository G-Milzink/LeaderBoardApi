package com.milzink.leaderboard_api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.milzink.leaderboard_api.services.TOAO_PlayerListService;
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
@RequestMapping("/toao/playerlist")
public class TOAO_PlayerRegistrationController {

    private final TOAO_PlayerListService toaoPlayerListService;

    public TOAO_PlayerRegistrationController(TOAO_PlayerListService toaoPlayerListService) {
        this.toaoPlayerListService = toaoPlayerListService;
    }

    
   @PostMapping("/addplayer")
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


       try {
           toaoPlayerListService.addPlayer(
                   newPlayer.get("playerId"),
                   newPlayer.get("password"),
                   newPlayer.get("email")
           );
            return ResponseEntity.ok("New player stored succesfully!");
       } catch (IllegalArgumentException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured: " + e.getMessage());
       }
   }
}