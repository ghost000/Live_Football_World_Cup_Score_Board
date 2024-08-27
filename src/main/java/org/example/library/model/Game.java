package org.example.library.model;

import java.time.Instant;

public class Game {
    private final String homeTeamName;
    private final String guestTeamName;
    private final Score score = new Score();
    private final long startTime;

    public Game(String homeTeamName, String guestTeamName, Long startTime) {
        this.homeTeamName = homeTeamName;
        this.guestTeamName = guestTeamName;
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
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

    @Override
    public String toString() {
        return " homeTeamName : " + homeTeamName
                + " getHomeTeamGoals : " + score.getHomeTeamGoals() + " - "
                + " guestTeamName : " + guestTeamName + " getGuestTeamGoals : " + score.getGuestTeamGoals();
    }
}
