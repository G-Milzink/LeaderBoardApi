package com.milzink.leaderboard_api.utillities;

public class ScoreDTO {

    static String playerId;
    static Integer score;

    public ScoreDTO() {
    }

    public ScoreDTO(String playerId, int score) {
        ScoreDTO.playerId = playerId;
        ScoreDTO.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        ScoreDTO.score = score;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        ScoreDTO.playerId = playerId;
    }
}
