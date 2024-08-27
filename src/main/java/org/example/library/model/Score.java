package org.example.library.model;

import java.time.LocalDateTime;

public class Score {
    private int homeTeamGoals = 0;
    private int guestTeamGoals = 0;
    private LocalDateTime creationTime = LocalDateTime.now();

    public Score(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Score() {}

    public int getGuestTeamGoals() {
        return guestTeamGoals;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void updateScore(int homeTeamGoals, int guestTeamGoals) {
        this.guestTeamGoals = guestTeamGoals;
        this.homeTeamGoals = homeTeamGoals;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public int getTotalScore() {
        return this.homeTeamGoals + this.guestTeamGoals;
    }

}
