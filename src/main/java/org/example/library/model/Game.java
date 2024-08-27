package org.example.library.model;

import java.time.LocalDateTime;

public class Game {
    private final String homeTeamName;
    private final String guestTeamName;
    private final Score score = new Score();
    private LocalDateTime startTime = LocalDateTime.now();

    public Game(String homeTeamName, String guestTeamName) {
        this.homeTeamName = homeTeamName;
        this.guestTeamName = guestTeamName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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
