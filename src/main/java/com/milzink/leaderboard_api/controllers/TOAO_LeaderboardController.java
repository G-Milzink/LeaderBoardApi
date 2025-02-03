package com.milzink.leaderboard_api.controllers;

import com.milzink.leaderboard_api.services.TOAO_LeaderboardService;
import com.milzink.leaderboard_api.utillities.TOAO_ScoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/toao/leaderboards")
public class TOAO_LeaderboardController {

    private final TOAO_LeaderboardService toao_leaderboardService;

    public TOAO_LeaderboardController(TOAO_LeaderboardService leaderboardService) {
        this.toao_leaderboardService = leaderboardService;
    }


    @GetMapping("/getfullboard")
    public List<Map.Entry<String, TOAO_ScoreDTO>> leaderboardFull() {
        return toao_leaderboardService.getAllScoresSorted();
    }

}
