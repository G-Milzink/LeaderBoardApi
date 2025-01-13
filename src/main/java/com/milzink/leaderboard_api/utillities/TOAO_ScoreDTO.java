package com.milzink.leaderboard_api.utillities;

public class TOAO_ScoreDTO {

    static String playerId;
    static Integer score;

    public TOAO_ScoreDTO() {
    }

    public TOAO_ScoreDTO(String playerId, int score) {
        TOAO_ScoreDTO.playerId = playerId;
        TOAO_ScoreDTO.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        TOAO_ScoreDTO.score = score;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        TOAO_ScoreDTO.playerId = playerId;
    }
}
