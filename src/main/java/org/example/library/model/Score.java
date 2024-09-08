package org.example.library.model;

public class Score {
    private int homeTeamGoals = 0;
    private int awayTeamGoals = 0;

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void updateScore(int homeTeamGoals, int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
        this.homeTeamGoals = homeTeamGoals;
    }
}
