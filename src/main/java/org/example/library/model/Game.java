package org.example.library.model;

public class Game {
    private final String homeTeamName;
    private final String guestTeamName;
    private final Score score = new Score();
    private final long startTime;
    private final String id;

    public Game(String homeTeamName, String guestTeamName, Long startTime, String id) {
        this.homeTeamName = homeTeamName;
        this.guestTeamName = guestTeamName;
        this.startTime = startTime;
        this.id = id;
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

    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return " homeTeamName : " + homeTeamName
                + " getHomeTeamGoals : " + score.getHomeTeamGoals() + " - "
                + " guestTeamName : " + guestTeamName + " getGuestTeamGoals : " + score.getGuestTeamGoals();
    }
}
