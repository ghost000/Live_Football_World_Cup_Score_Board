package org.example.library.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreTests {
    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @Test
    void testDefaultConstructor() {
        assertEquals(0, score.getHomeTeamGoals());
        assertEquals(0, score.getGuestTeamGoals());
    }

    @Test
    void testUpdateScore() {
        score.updateScore(3, 2);

        assertEquals(3, score.getHomeTeamGoals());
        assertEquals(2, score.getGuestTeamGoals());
        assertEquals(5, score.getHomeTeamGoals() + score.getGuestTeamGoals());
    }

    @Test
    void testGetTotalScore() {
        score.updateScore(4, 1);

        assertEquals(5, score.getHomeTeamGoals() + score.getGuestTeamGoals());
    }
}