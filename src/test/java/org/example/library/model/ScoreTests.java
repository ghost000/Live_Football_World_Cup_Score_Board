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
        assertEquals(0, score.homeTeamGoals() + score.awayTeamGoals());
    }
}