package org.example.library.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private final static AtomicInteger idCounter = new AtomicInteger(1);

    private final String homeTeamName;
    private final String guestTeamName;
    private final Score score = new Score();
    private final int id;

    public Game(String homeTeamName, String guestTeamName) {
        this.homeTeamName = homeTeamName;
        this.guestTeamName = guestTeamName;
        this.id = idCounter.getAndIncrement();
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getGuestTeamName() {
        return guestTeamName;
    }

    public Score getScore() {
        return score;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return " homeTeamName : " + homeTeamName
                + " getHomeTeamGoals : " + score.getHomeTeamGoals() + " - "
                + " guestTeamName : " + guestTeamName + " getGuestTeamGoals : " + score.getAwayTeamGoals();
    }
}
