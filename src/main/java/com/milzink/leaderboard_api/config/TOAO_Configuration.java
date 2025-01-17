package com.milzink.leaderboard_api.config;

import org.springframework.context.annotation.Configuration;

import com.milzink.leaderboard_api.services.TOAO_PlayerListService;
import com.milzink.leaderboard_api.services.TOAO_DataCleanUpService;

@Configuration
public class TOAO_Configuration {

    private  TOAO_DataCleanUpService toaoDataCleanUpService;
    private  TOAO_PlayerListService toaoPlayerListService;

    public TOAO_Configuration(
            TOAO_DataCleanUpService cleanUpService,
            TOAO_PlayerListService listService
    ) {
        this.toaoDataCleanUpService = cleanUpService;
        this.toaoPlayerListService = listService;
    }
}
