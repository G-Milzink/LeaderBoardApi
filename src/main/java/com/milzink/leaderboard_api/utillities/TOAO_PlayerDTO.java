package com.milzink.leaderboard_api.utillities;

public class TOAO_PlayerDTO {

    String password;
    String email;

    public TOAO_PlayerDTO() {
    }

    public TOAO_PlayerDTO(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
}
