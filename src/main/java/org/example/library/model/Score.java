package org.example.library.model;

public final class Score {
    private final int homeTeamGoals;
    private final int awayTeamGoals;

    public Score(int homeTeamGoals, int awayTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public Score updateScore(int homeTeamGoals, int awayTeamGoals) {
        return new Score(homeTeamGoals, awayTeamGoals);
    }
}
