package com.milzink.leaderboard_api.controllers;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.milzink.leaderboard_api.services.TOAO_PlayerListService;

@RestController
@RequestMapping("/toao/playerlist")
public class TOAO_PLayerLoginController {

    private final TOAO_PlayerListService toaoPlayerListService;

    public TOAO_PLayerLoginController(TOAO_PlayerListService playerListService) {
        this.toaoPlayerListService = playerListService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> getPlayer(@RequestBody Map<String, String> playerData) {

        if (toaoPlayerListService.getPlayer(playerData.get("playerId"), playerData.get("password"))) {
           return ResponseEntity.ok("Player logged in successfully!");
        }
        return  ResponseEntity.badRequest().body("Invalid credentials!");
    }



}
