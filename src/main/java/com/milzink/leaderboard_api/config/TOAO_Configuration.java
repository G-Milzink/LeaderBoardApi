package com.milzink.leaderboard_api.config;


import com.milzink.leaderboard_api.services.TOAO_PlayerListService;
import org.springframework.context.annotation.Configuration;
import com.milzink.leaderboard_api.services.TOAO_DataCleanUpService;

@Configuration
public class TOAO_Configuration {

    private TOAO_DataCleanUpService dataCleanUpService;
    private TOAO_PlayerListService playerListService;

    public TOAO_Configuration(
            TOAO_DataCleanUpService cleanUpService,
            TOAO_PlayerListService listService
    ) {
        this.dataCleanUpService = cleanUpService;
        this.playerListService = listService;
    }
}
