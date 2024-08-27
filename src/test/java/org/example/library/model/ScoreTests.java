package org.example.library.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @Test
    void testDefaultConstructor() {
        Score score = new Score();

        assertEquals(0, score.getHomeTeamGoals());
        assertEquals(0, score.getGuestTeamGoals());
    }

    @Test
    void testUpdateScore() {
        Score score = new Score();
        score.updateScore(3, 2);

        assertEquals(3, score.getHomeTeamGoals());
        assertEquals(2, score.getGuestTeamGoals());
        assertEquals(5, score.getTotalScore());
    }

    @Test
    void testGetTotalScore() {
        Score score = new Score();
        score.updateScore(4, 1);

        assertEquals(5, score.getTotalScore());
    }
}