package com.milzink.leaderboard_api.utillities;

public class TOAO_PlayerLoginDTO {

    private String playerId;
    private String password;

    public TOAO_PlayerLoginDTO() {
    }

    public TOAO_PlayerLoginDTO(String playerId, String password) {
        this.playerId = playerId;
        this.password = password;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
