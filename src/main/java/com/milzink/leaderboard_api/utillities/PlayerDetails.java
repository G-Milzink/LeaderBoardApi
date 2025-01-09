package com.milzink.leaderboard_api.utillities;

public class PlayerDetails {

    String password;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PlayerDetails(String password, String email) {
        this.password = password;
        this. email = email;
    }

}
