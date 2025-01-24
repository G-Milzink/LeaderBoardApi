package com.milzink.leaderboard_api.utillities;

import java.time.LocalDateTime;

public class TOAO_ScoreDTO {

    private String playerId;
    private Integer score;
    private LocalDateTime timestamp;

    public TOAO_ScoreDTO() {
    }

    public TOAO_ScoreDTO(String playerId, int score, LocalDateTime timestamp) {
        this.playerId = playerId;
        this.score = score;
        this.timestamp = timestamp;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
