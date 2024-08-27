package org.example.library.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GameTests {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game("Home Team", "Guest Team");
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game.getHomeTeamName());
        assertEquals("Home Team", game.getHomeTeamName());

        assertNotNull(game.getGuestTeamName());
        assertEquals("Guest Team", game.getGuestTeamName());

        assertNotNull(game.getScore());
        assertEquals(0, game.getScore().getHomeTeamGoals());
        assertEquals(0, game.getScore().getGuestTeamGoals());

        assertNotNull(game.getStartTime());
    }

    @Test
    void testSetStartTime() {
        LocalDateTime newStartTime = LocalDateTime.of(2024, 8, 23, 10, 0);
        game.setStartTime(newStartTime);
        assertEquals(newStartTime, game.getStartTime());
    }

    @Test
    void testUpdateScore() {
        game.getScore().updateScore(2, 3);
        assertEquals(2, game.getScore().getHomeTeamGoals());
        assertEquals(3, game.getScore().getGuestTeamGoals());
        assertEquals(5, game.getScore().getTotalScore());
    }

    @Test
    void testToString() {
        game.getScore().updateScore(2, 1);
        String expectedString = " homeTeamName : Home Team getHomeTeamGoals : 2 -  guestTeamName : Guest Team getGuestTeamGoals : 1";
        assertEquals(expectedString, game.toString());
    }
}
