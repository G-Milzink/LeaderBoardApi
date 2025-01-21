package com.milzink.leaderboard_api.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.milzink.leaderboard_api.services.TOAO_PlayerListService;

@RestController
@RequestMapping("/toao/playerlist")
public class TOAO_PlayerRegistrationController {

    private final TOAO_PlayerListService toaoPlayerListService;

    public TOAO_PlayerRegistrationController(TOAO_PlayerListService playerListService) {
        this.toaoPlayerListService = playerListService;
    }

    
   @PostMapping("/addplayer")
   public ResponseEntity<String> addPlayer(@RequestBody Map<String, String> newPlayer) {

       if (newPlayer.get("playerId").isBlank()) {
           return ResponseEntity.badRequest().body("PlayerId field can not be blank.");
       }
       if (newPlayer.get("password").isBlank()) {
           return ResponseEntity.badRequest().body("Password field can not be blank.");
       }
//       if (newPlayer.get("email").isBlank()) {
//           return ResponseEntity.badRequest().body("No email provided.");
//       }


       try {
           toaoPlayerListService.addPlayer(
                   newPlayer.get("playerId"),
                   newPlayer.get("password"),
                   newPlayer.get("email")
           );
            return ResponseEntity.ok("New player stored successfully!");
       } catch (IllegalArgumentException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured: " + e.getMessage());
       }
   }

    @PostMapping("/removeplayer")
    public ResponseEntity<String> deletePlayer(@RequestBody Map<String, String> playerLogin) {

        if (toaoPlayerListService.deletePlayer(playerLogin.get("playerId"), playerLogin.get("password"))) {
            return ResponseEntity.ok("Player removed in successfully!");
        }
        return  ResponseEntity.badRequest().body("Invalid credentials!");
    }

}