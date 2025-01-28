package com.milzink.leaderboard_api.services;


import com.milzink.leaderboard_api.utillities.TOAO_ScoreDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TOAO_LeaderboardService {

    Map<String, TOAO_ScoreDTO> TOAO_Leaderboard_ScoreList = new HashMap<>();
    private final TOAO_ScoreListService toaoScoreListService;

    public TOAO_LeaderboardService(TOAO_ScoreListService scoreListService) {
        this.toaoScoreListService = scoreListService;
    }

    @Value("${data.folder}")
    private String dataFolder;

    @PostConstruct
    public void init() {
        System.out.println("TOAO_LeaderboardService started");

        TOAO_Leaderboard_ScoreList = toaoScoreListService.getScoreList();
        System.out.println(TOAO_Leaderboard_ScoreList);

    }

}
