package org.example.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LiveFootballWorldCupScoreBoardLibraryTests {
    private LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary;

    @BeforeEach
    void setUp() {
        scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();
    }

    @Test
    void shouldCreateLibrary() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 0, 1);

        var summary = scoreBoardLibrary.getASummary();
        assertEquals(1, summary.size());
        assertEquals(0, summary.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, summary.getFirst().getScore().getGuestTeamGoals());
    }

    @Test
    void updateScoreShouldCorrectlyUpdateOngoingMatch() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 2, 1);

        var summary = scoreBoardLibrary.getASummary();
        assertEquals(1, summary.size());
        assertEquals(2, summary.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, summary.getFirst().getScore().getGuestTeamGoals());
    }

    @Test
    void updateScoreShouldFailForNonexistentMatch() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.updateScore(1234567890, 0, 1);
        });
    }

    @Test
    void updateScoreShouldAllowLoweringScore() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 3, 2);
        scoreBoardLibrary.updateScore(id, 2, 1);

        var summary = scoreBoardLibrary.getASummary();
        assertEquals(1, summary.size());
        assertEquals(2, summary.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, summary.getFirst().getScore().getGuestTeamGoals());
    }

    @Test
    void updateScoreShouldHandleNegativeScoresGracefully() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.updateScore(id, -10, -1);
        });
    }

    @Test
    void getSummaryShouldReturnGamesInCorrectOrder() {
        final int id_1 = scoreBoardLibrary.addNewGame("Mexico", "Canada" );
        scoreBoardLibrary.updateScore(id_1, 0, 5);

        final int id_2 = scoreBoardLibrary.addNewGame("Spain", "Brazil" );
        scoreBoardLibrary.updateScore(id_2, 10, 2);

        final int id_3 = scoreBoardLibrary.addNewGame("Germany", "France" );
        scoreBoardLibrary.updateScore(id_3, 2, 2);

        final int id_4 = scoreBoardLibrary.addNewGame("Uruguay", "Italy" );
        scoreBoardLibrary.updateScore(id_4, 6, 6);

        final int id_5 = scoreBoardLibrary.addNewGame("Argentina", "Australia" );
        scoreBoardLibrary.updateScore(id_5, 3, 1);

        var summary = scoreBoardLibrary.getASummary();

        assertEquals(5, scoreBoardLibrary.getASummary().size());

        assertEquals("Uruguay", summary.get(0).getHomeTeamName());
        assertEquals("Italy", summary.get(0).getGuestTeamName());

        assertEquals("Spain", summary.get(1).getHomeTeamName());
        assertEquals("Brazil", summary.get(1).getGuestTeamName());

        assertEquals("Mexico", summary.get(2).getHomeTeamName());
        assertEquals("Canada", summary.get(2).getGuestTeamName());

        assertEquals("Argentina", summary.get(3).getHomeTeamName());
        assertEquals("Australia", summary.get(3).getGuestTeamName());

        assertEquals("Germany", summary.get(4).getHomeTeamName());
        assertEquals("France", summary.get(4).getGuestTeamName());
    }

    @Test
    void finishGameShouldRemoveMatchFromScoreboard() {
        final int id_1 = scoreBoardLibrary.addNewGame("home team 1", "away team 1" );
        final int id_2 = scoreBoardLibrary.addNewGame("home team 2", "away team 2" );
        final int id_3 = scoreBoardLibrary.addNewGame("home team 3", "away team 3" );

        scoreBoardLibrary.updateScore(id_1, 1, 1);
        scoreBoardLibrary.updateScore(id_2, 2, 2);
        scoreBoardLibrary.updateScore(id_3, 3, 3);

        scoreBoardLibrary.finishGame(id_2);

        var summary = scoreBoardLibrary.getASummary();

        assertEquals(2, summary.size());

        assertEquals("home team 3", summary.get(0).getHomeTeamName());
        assertEquals("away team 3", summary.get(0).getGuestTeamName());

        assertEquals("home team 1", summary.get(1).getHomeTeamName());
        assertEquals("away team 1", summary.get(1).getGuestTeamName());
    }

    @Test
    void updateScoreShouldHandleMultipleMatchesSimultaneously() {
        final int id_1 = scoreBoardLibrary.addNewGame("Mexico", "Canada" );
        final int id_2 = scoreBoardLibrary.addNewGame("Spain", "Brazil" );
        final int id_3 = scoreBoardLibrary.addNewGame("Germany", "France" );
        final int id_4 = scoreBoardLibrary.addNewGame("Uruguay", "Italy" );
        final int id_5 = scoreBoardLibrary.addNewGame("Argentina", "Australia" );

        scoreBoardLibrary.updateScore(id_1, 2, 6);
        scoreBoardLibrary.updateScore(id_2, 11, 3);
        scoreBoardLibrary.updateScore(id_3, 3, 4);
        scoreBoardLibrary.updateScore(id_4, 7, 6);
        scoreBoardLibrary.updateScore(id_5, 4, 5);

        scoreBoardLibrary.updateScore(id_1, 3, 7);
        scoreBoardLibrary.updateScore(id_2, 14, 4);
        scoreBoardLibrary.updateScore(id_3, 4, 5);
        scoreBoardLibrary.updateScore(id_4, 8, 6);
        scoreBoardLibrary.updateScore(id_5, 5, 5);


        scoreBoardLibrary.updateScore(id_1, 5, 7);
        scoreBoardLibrary.updateScore(id_2, 15, 7);
        scoreBoardLibrary.updateScore(id_3, 5, 6);
        scoreBoardLibrary.updateScore(id_4, 9, 6);
        scoreBoardLibrary.updateScore(id_5, 6, 5);

        var summary = scoreBoardLibrary.getASummary();

        assertEquals(5, summary.size());

        assertEquals("Spain", summary.get(0).getHomeTeamName());
        assertEquals("Brazil", summary.get(0).getGuestTeamName());

        assertEquals("Uruguay", summary.get(1).getHomeTeamName());
        assertEquals("Italy", summary.get(1).getGuestTeamName());

        assertEquals("Mexico", summary.get(2).getHomeTeamName());
        assertEquals("Canada", summary.get(2).getGuestTeamName());

        assertEquals("Argentina", summary.get(3).getHomeTeamName());
        assertEquals("Australia", summary.get(3).getGuestTeamName());

        assertEquals("Germany", summary.get(4).getHomeTeamName());
        assertEquals("France", summary.get(4).getGuestTeamName());
    }

    @Test
    void updateScoreShouldNotChangeWhenSameScoreIsGiven() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);

        var summary = scoreBoardLibrary.getASummary();
        assertEquals(1, summary.size());
        assertEquals(0, summary.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, summary.getFirst().getScore().getGuestTeamGoals());
    }

    @Test
    void addNewGameShouldThrowExceptionForIdenticalTeamNames() {
        assertThrows(IllegalArgumentException.class, () -> {
            final int id = scoreBoardLibrary.addNewGame("TeamA", "TeamA" );
        });
    }

    @Test
    void addNewGameShouldThrowExceptionForNullTeamNames() {
        assertThrows(NullPointerException.class, () -> {
            final int id = scoreBoardLibrary.addNewGame(null, "TeamB" );
        });
        assertThrows(NullPointerException.class, () -> {
            final int id = scoreBoardLibrary.addNewGame("TeamA", null);
        });
    }

    @Test
    void updateScoreShouldThrowExceptionForNegativeScores() {
        final int id = scoreBoardLibrary.addNewGame("TeamA", "TeamB" );
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.updateScore(id, -1, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.updateScore(id, 2, -3);
        });
    }

    @Test
    void updateScoreShouldThrowExceptionForNonExistentGame() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.updateScore(1234567890, 1, 2);
        });
    }

    @Test
    void finishGameShouldThrowExceptionForNonExistentGame() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.finishGame(1234567890 );
        });
    }
}