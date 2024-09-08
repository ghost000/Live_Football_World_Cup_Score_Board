package org.example.library.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTests {
    @Test
    void validateScoreShouldThrowExceptionWhenScoreIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validator.validateScore(-1, "Home"));
        assertEquals("Home team score cannot be negative.", exception.getMessage());
    }

    @Test
    void validateScoreShouldPassWhenScoreIsZero() {
        assertDoesNotThrow(() -> Validator.validateScore(0, "Guest"));
    }

    @Test
    void validateScoreShouldPassWhenScoreIsPositive() {
        assertDoesNotThrow(() -> Validator.validateScore(5, "Home"));
    }

    @Test
    void validateTeamNamesShouldThrowExceptionWhenBothTeamsHaveSameName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validator.validateTeamNames("Spain", "Spain"));
        assertEquals("Home team and guest team names must be different.", exception.getMessage());
    }

    @Test
    void validateTeamNamesShouldThrowExceptionWhenHomeTeamNameIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> Validator.validateTeamNames(null, "Brazil"));
        assertEquals("Home team name cannot be null.", exception.getMessage());
    }

    @Test
    void validateTeamNamesShouldThrowExceptionWhenGuestTeamNameIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validator.validateTeamNames("Argentina", ""));
        assertEquals("Guest team name cannot be empty.", exception.getMessage());
    }

    @Test
    void validateTeamNamesShouldPassWhenTeamNamesAreValidAndDifferent() {
        assertDoesNotThrow(() -> Validator.validateTeamNames("Germany", "France"));
    }
}
