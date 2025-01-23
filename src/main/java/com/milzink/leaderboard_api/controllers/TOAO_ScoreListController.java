package com.milzink.leaderboard_api.controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.milzink.leaderboard_api.services.TOAO_ScoreListService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;

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
}
