package org.example.library.utils;

import java.util.Objects;

public class Validator {

    private static void validateTeamName(String teamName, String teamType) {
        Objects.requireNonNull(teamName, teamType + " team name cannot be null.");
        if (teamName.trim().isEmpty()) {
            throw new IllegalArgumentException(teamType + " team name cannot be empty.");
        }
    }

    public static void validateScore(int score, String teamType) {
        if (score < 0) {
            throw new IllegalArgumentException(teamType + " team score cannot be negative.");
        }
    }

    public static void validateTeamNames(String homeTeamName, String guestTeamName) {
        validateTeamName(homeTeamName, "Home");
        validateTeamName(guestTeamName, "Guest");

        final String normalizedHomeTeamName = homeTeamName.trim();
        final String normalizedGuestTeamName = guestTeamName.trim();

        if (normalizedHomeTeamName.equalsIgnoreCase(normalizedGuestTeamName)) {
            throw new IllegalArgumentException("Home team and guest team names must be different.");
        }
    }
}
