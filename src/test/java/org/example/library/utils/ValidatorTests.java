package org.example.library.utils;

import org.example.library.model.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        final List<Game> games = new ArrayList<>();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validator.validateTeamNames("Spain", "Spain", games));
        assertEquals("Home team and guest team names must be different.", exception.getMessage());
    }

    @Test
    void validateTeamNamesShouldThrowExceptionWhenHomeTeamNameIsNull() {
        final List<Game> games = new ArrayList<>();
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> Validator.validateTeamNames(null, "Brazil", games));
        assertEquals("Home team name cannot be null.", exception.getMessage());
    }

    @Test
    void validateTeamNamesShouldThrowExceptionWhenGuestTeamNameIsEmpty() {
        final List<Game> games = new ArrayList<>();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validator.validateTeamNames("Argentina", "", games));
        assertEquals("Guest team name cannot be empty.", exception.getMessage());
    }

    @Test
    void validateTeamNamesShouldThrowExceptionWhenBothTeamsHaveAlmostTheSameName() {
        final List<Game> games = new ArrayList<>();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validator.validateTeamNames("England", "England ", games));
        assertEquals("Home team and guest team names must be different.", exception.getMessage());
    }

    @Test
    void validateTeamNamesShouldPassWhenTeamNamesAreValidAndDifferent() {
        final List<Game> games = new ArrayList<>();
        assertDoesNotThrow(() -> Validator.validateTeamNames("Germany", "France", games));
    }

    @Test
    void validateTeamNamesShouldThrowExceptionWhenOneTeamIsAlreadyPlaying() {
        final List<Game> games = new ArrayList<>();
        games.add(new Game("England", "France"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Validator.validateTeamNames("England", "Argentina", games));
        assertEquals("Team England is already playing.", exception.getMessage());
    }
}
