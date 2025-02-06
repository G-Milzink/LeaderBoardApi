package com.milzink.leaderboard_api.controllers;

import com.milzink.leaderboard_api.services.TOAO_LeaderboardService;
import com.milzink.leaderboard_api.utillities.TOAO_ScoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/toao/leaderboards")
public class TOAO_LeaderboardController {

    private final TOAO_LeaderboardService toao_leaderboardService;

    public TOAO_LeaderboardController(TOAO_LeaderboardService leaderboardService) {
        this.toao_leaderboardService = leaderboardService;
    }

    @GetMapping("/updateleaderboard")
    public void  loadLeaderboard() {
        toao_leaderboardService.loadScoreList();
    }

    @GetMapping("/getfullboard")
    public List<Map.Entry<String, TOAO_ScoreDTO>> leaderboardFull() {
        return toao_leaderboardService.getAllScoresSorted();
    }

    @GetMapping("/getlimitedboard")
    public ResponseEntity<List<Map.Entry<String, TOAO_ScoreDTO>>> getLimitedBoard(@RequestParam int limit) {
        List<Map.Entry<String, TOAO_ScoreDTO>> sortedScores = toao_leaderboardService.getTopScores(limit);
        return ResponseEntity.ok(sortedScores);
    }
}



