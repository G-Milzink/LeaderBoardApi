package com.milzink.leaderboard_api.utillities;

public class TOAO_ScoreDTO {

    private String playerId;
    private Integer score;

    public TOAO_ScoreDTO() {
    }

    public TOAO_ScoreDTO(String playerId, int score) {
        this.playerId = playerId;
        this.score = score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

}
