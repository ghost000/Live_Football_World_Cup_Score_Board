package org.example.library.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreTests {
    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score(0,0);
    }

    @Test
    void testDefaultConstructor() {
        assertEquals(0, score.getHomeTeamGoals());
        assertEquals(0, score.getAwayTeamGoals());
    }

    @Test
    void testUpdateScore() {
        score = score.updateScore(3, 2);

        assertEquals(3, score.getHomeTeamGoals());
        assertEquals(2, score.getAwayTeamGoals());
        assertEquals(5, score.getHomeTeamGoals() + score.getAwayTeamGoals());
    }

    @Test
    void testGetTotalScore() {
        score = score.updateScore(4, 1);

        assertEquals(5, score.getHomeTeamGoals() + score.getAwayTeamGoals());
    }
}