package com.milzink.leaderboard_api.utillities;

public class TOOAY_ScoreDTO {

    static String playerId;
    static Integer score;

    public TOOAY_ScoreDTO() {
    }

    public TOOAY_ScoreDTO(String playerId, int score) {
        TOOAY_ScoreDTO.playerId = playerId;
        TOOAY_ScoreDTO.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        TOOAY_ScoreDTO.score = score;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        TOOAY_ScoreDTO.playerId = playerId;
    }
}
