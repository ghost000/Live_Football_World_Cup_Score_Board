package org.example.library.utils;

import org.example.library.model.Game;

import java.util.List;
import java.util.Objects;

public class Validator {

    private static void validateTeamName(String teamName, String teamType) {
        Objects.requireNonNull(teamName, teamType + " team name cannot be null." );
        if (teamName.isEmpty()) {
            throw new IllegalArgumentException(teamType + " team name cannot be empty." );
        }
    }

    private static void validateIsThisTeamPlayingRightNow(String teamName, List<Game> games) {
        if (games.stream().anyMatch(game -> game.getHomeTeamName().equalsIgnoreCase(teamName)
                || game.getGuestTeamName().equalsIgnoreCase(teamName))) {
            throw new IllegalArgumentException("Team " + teamName + " is already playing." );
        }
    }

    public static void validateScore(int score, String teamType) {
        if (score < 0) {
            throw new IllegalArgumentException(teamType + " team score cannot be negative." );
        }
    }

    public static void validateTeamNames(String homeTeamName, String guestTeamName, List<Game> games) {
        validateTeamName(homeTeamName, "Home" );
        validateTeamName(guestTeamName, "Guest" );

        final String normalizedHomeTeamName = homeTeamName.trim();
        final String normalizedGuestTeamName = guestTeamName.trim();

        validateIsThisTeamPlayingRightNow(normalizedHomeTeamName, games);
        validateIsThisTeamPlayingRightNow(normalizedGuestTeamName, games);

        if (normalizedHomeTeamName.equalsIgnoreCase(normalizedGuestTeamName)) {
            throw new IllegalArgumentException("Home team and guest team names must be different." );
        }
    }

}
