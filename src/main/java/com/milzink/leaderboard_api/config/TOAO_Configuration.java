package com.milzink.leaderboard_api.config;


import org.springframework.context.annotation.Configuration;
import com.milzink.leaderboard_api.services.TOAO_DataCleanUpService;

@Configuration
public class TOAO_Configuration {

    private TOAO_DataCleanUpService cleanUpService;

    public TOAO_Configuration(TOAO_DataCleanUpService cleanUpService) {
        this.cleanUpService = cleanUpService;
    }

}
