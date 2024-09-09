package org.example.library.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTests {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game("Home Team", "Away Team");
    }

    @Test
    void testGameInitialization() {
        assertEquals("Home Team", game.getHomeTeamName());
        assertEquals("Away Team", game.getAwayTeamName());
        assertEquals(0, game.getScore().homeTeamGoals());
        assertEquals(0, game.getScore().awayTeamGoals());
    }

    @Test
    void testUpdateScore() {
        game = game.updateScore(2,3);
        assertEquals(5, game.getScore().homeTeamGoals() + game.getScore().awayTeamGoals());
    }

    @Test
    void testToString() {
        game = game.updateScore(2, 1);
        assertEquals(" homeTeamName : Home Team getHomeTeamGoals : 2 -  awayTeamName : Away Team getAwayTeamGoals : 1", game.toString());
    }
}
