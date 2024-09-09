package org.example.library.model;

import java.util.concurrent.atomic.AtomicInteger;

public final class Game {
    private final static AtomicInteger idCounter = new AtomicInteger(1);

    private final String homeTeamName;
    private final String awayTeamName;
    private final Score score;
    private final int id;

    public Game(String homeTeamName, String awayTeamName) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.score = new Score(0,0);
        this.id = idCounter.getAndIncrement();
    }

    public Game(String homeTeamName, String awayTeamName, Score score, int id) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.score = score;
        this.id = id;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public Score getScore() {
        return score;
    }

    public int getID() {
        return id;
    }

    public Game updateScore(int homeTeamGoals, int awayTeamGoals) {
        return new Game(this.homeTeamName, this.awayTeamName, new Score(homeTeamGoals, awayTeamGoals), this.id);
    }

    @Override
    public String toString() {
        return " homeTeamName : " + homeTeamName
                + " getHomeTeamGoals : " + score.homeTeamGoals() + " - "
                + " awayTeamName : " + awayTeamName + " getAwayTeamGoals : " + score.awayTeamGoals();
    }
}
