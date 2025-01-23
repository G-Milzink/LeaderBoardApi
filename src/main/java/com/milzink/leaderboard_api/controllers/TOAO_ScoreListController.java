package com.milzink.leaderboard_api.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.milzink.leaderboard_api.services.TOAO_ScoreListService;
import com.milzink.leaderboard_api.utillities.TOAO_ScoreDTO;

@RestController
@RequestMapping("/toao/scorelist")
public class TOAO_ScoreListController {

    private final TOAO_ScoreListService toaoScoreListService;

    public TOAO_ScoreListController(TOAO_ScoreListService scoreListService) {
        this.toaoScoreListService = scoreListService;
    }

    @PostMapping("/addscore")
    public ResponseEntity<String> postScore(@RequestBody TOAO_ScoreDTO newScore) {

        if (newScore.getPlayerId() == null) {
            return ResponseEntity.badRequest().body("PlayerId can not be null.");
        }
        if (newScore.getScore() == null) {
            return ResponseEntity.badRequest().body("Score can not be null.");
        }

        try {
            toaoScoreListService.addScore(
                    newScore.getPlayerId(),
                    newScore.getScore()
            );
            return ResponseEntity.ok("New score stored successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured: " + e.getMessage());
        }
    }

    @PostMapping("/getscore")
    public ResponseEntity<Integer> getPlayer(@RequestBody Map<String, String> playerId) {
        return ResponseEntity.ok(toaoScoreListService.getScore(playerId.get("playerId")));
    }
}
