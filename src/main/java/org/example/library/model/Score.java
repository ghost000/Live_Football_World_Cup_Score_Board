package org.example.library.model;

public class Score {
    private int homeTeamGoals = 0;
    private int guestTeamGoals = 0;

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

    public int getTotalScore() {
        return this.homeTeamGoals + this.guestTeamGoals;
    }

}
