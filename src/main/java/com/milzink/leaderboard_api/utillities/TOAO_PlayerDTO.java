package com.milzink.leaderboard_api.utillities;

import java.time.LocalDate;

public class TOAO_PlayerDTO {

    private String password;
    private String email;

    private LocalDate lastLogin = LocalDate.now();

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

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDate getLastLogin() {
        return this.lastLogin;
    }

}
