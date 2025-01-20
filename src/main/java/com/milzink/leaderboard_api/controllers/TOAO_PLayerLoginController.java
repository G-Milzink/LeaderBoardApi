package com.milzink.leaderboard_api.controllers;

import com.milzink.leaderboard_api.services.TOAO_PlayerListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toao/login")
public class TOAO_PLayerLoginController {

    private final TOAO_PlayerListService toaoPlayerListService;

    public TOAO_PLayerLoginController(TOAO_PlayerListService playerListService) {
        this.toaoPlayerListService = playerListService;
    }

}
