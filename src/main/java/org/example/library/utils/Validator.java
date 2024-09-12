package org.example.library.utils;

import org.example.library.model.Game;

import java.util.List;
import java.util.Objects;

public final class Validator {

    private static void validateTeamName(String teamName, String teamType) {
        Objects.requireNonNull(teamName, teamType + " team name cannot be null." );
        if (teamName.isEmpty()) {
            throw new IllegalArgumentException(teamType + " team name cannot be empty." );
        }
    }

    private static void validateIsThisTeamPlayingRightNow(String teamName, List<Game> games) {
        if (games.stream().anyMatch(game -> game.getHomeTeamName().equalsIgnoreCase(teamName)
                || game.getAwayTeamName().equalsIgnoreCase(teamName))) {
            throw new IllegalArgumentException("Team " + teamName + " is already playing." );
        }
    }

    public static void validateScore(int score, String teamType) {
        if (score < 0) {
            throw new IllegalArgumentException(teamType + " team score cannot be negative." );
        }
    }

    public static void validateTeamNames(String homeTeamName, String awayTeamName, List<Game> games) {
        validateTeamName(homeTeamName, "Home" );
        validateTeamName(awayTeamName, "Away" );

        final String normalizedHomeTeamName = homeTeamName.trim();
        final String normalizedAwayTeamName = awayTeamName.trim();

        validateIsThisTeamPlayingRightNow(normalizedHomeTeamName, games);
        validateIsThisTeamPlayingRightNow(normalizedAwayTeamName, games);

        if (normalizedHomeTeamName.equalsIgnoreCase(normalizedAwayTeamName)) {
            throw new IllegalArgumentException("Home team and away team names must be different." );
        }
    }

}
