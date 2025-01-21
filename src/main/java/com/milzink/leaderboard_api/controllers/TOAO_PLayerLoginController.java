package com.milzink.leaderboard_api.controllers;

import com.milzink.leaderboard_api.services.TOAO_PlayerListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/toao/playerlist")
public class TOAO_PLayerLoginController {

    private final TOAO_PlayerListService toaoPlayerListService;

    public TOAO_PLayerLoginController(TOAO_PlayerListService playerListService) {
        this.toaoPlayerListService = playerListService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> getPlayer(@RequestBody Map<String, String> playerLogin) {

        if (toaoPlayerListService.getPlayer(playerLogin.get("playerId"), playerLogin.get("password"))) {
           return ResponseEntity.ok("Player logged in successfully!");
        }
        return  ResponseEntity.badRequest().body("Invalid credentials!");
    }
}
