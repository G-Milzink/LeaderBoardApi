package com.milzink.leaderboard_api.config;


import org.springframework.context.annotation.Configuration;
import com.milzink.leaderboard_api.services.TOAAY_DataCleanUpService;

@Configuration
public class TOAAY_Configuration {

    private TOAAY_DataCleanUpService cleanUpService;

    public TOAAY_Configuration(TOAAY_DataCleanUpService cleanUpService) {
        this.cleanUpService = cleanUpService;
    }

}
