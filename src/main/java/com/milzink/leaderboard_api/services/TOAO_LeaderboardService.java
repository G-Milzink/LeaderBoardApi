package com.milzink.leaderboard_api.services;


import com.milzink.leaderboard_api.utillities.TOAO_ScoreDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TOAO_LeaderboardService {

    Map<String, TOAO_ScoreDTO> TOAO_UnsortedScoreList = new HashMap<>();
    private final TOAO_ScoreListService toaoScoreListService;

    public TOAO_LeaderboardService(TOAO_ScoreListService scoreListService) {
        this.toaoScoreListService = scoreListService;
    }

    @Value("${data.folder}")
    private String dataFolder;

    @PostConstruct
    public void init() {
        System.out.println("TOAO_LeaderboardService started");
    }

    public void loadScoreList() {
        TOAO_UnsortedScoreList = toaoScoreListService.getScoreList();
    }

    public List<Map.Entry<String, TOAO_ScoreDTO>> getAllScoresSorted() {
        List<Map.Entry<String, TOAO_ScoreDTO>> scoreEntries = new ArrayList<>(TOAO_UnsortedScoreList.entrySet());
        scoreEntries.sort(TOAO_LeaderboardService::compareScores);
        return scoreEntries;
    }

    public List<Map.Entry<String, TOAO_ScoreDTO>> getTopScores(int limit) {
        List<Map.Entry<String, TOAO_ScoreDTO>> scoreEntries = getAllScoresSorted();
        int safeLimit = Math.min(limit, scoreEntries.size());
        List<Map.Entry<String, TOAO_ScoreDTO>> limitedList = scoreEntries.subList(0, safeLimit);
        return limitedList;
    }

    private static int compareScores(Map.Entry<String, TOAO_ScoreDTO> entry1, Map.Entry<String, TOAO_ScoreDTO> entry2) {
        TOAO_ScoreDTO score1 = entry1.getValue();
        TOAO_ScoreDTO score2 = entry2.getValue();
        int scoreComparison = Integer.compare(score2.getScore(), score1.getScore());
        if (scoreComparison != 0) {
            return scoreComparison;
        }
        return score1.getTimestamp().compareTo(score2.getTimestamp());
    }

}
