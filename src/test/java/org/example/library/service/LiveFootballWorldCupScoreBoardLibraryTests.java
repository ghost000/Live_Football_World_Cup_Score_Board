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
        final String id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 0, 1);

        var scoreBoard = scoreBoardLibrary.getScoreboard();
        assertEquals(1, scoreBoard.size());
        assertEquals(0, scoreBoard.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, scoreBoard.getFirst().getScore().getGuestTeamGoals());
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }

    @Test
    void updateScoreShouldCorrectlyUpdateOngoingMatch() {
        final String id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 2, 1);

        var scoreBoard = scoreBoardLibrary.getScoreboard();
        assertEquals(1, scoreBoard.size());
        assertEquals(2, scoreBoard.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, scoreBoard.getFirst().getScore().getGuestTeamGoals());
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }

    @Test
    void updateScoreShouldFailForNonexistentMatch() {
        final String id = scoreBoardLibrary.addNewGame("home team", "away team" );
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.updateScore("NonExistentGameID", 0, 1);
        });
    }

    @Test
    void updateScoreShouldAllowLoweringScore() {
        final String id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 3, 2);
        scoreBoardLibrary.updateScore(id, 2, 1);

        var scoreBoard = scoreBoardLibrary.getScoreboard();
        assertEquals(1, scoreBoard.size());
        assertEquals(2, scoreBoard.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, scoreBoard.getFirst().getScore().getGuestTeamGoals());
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }

    @Test
    void updateScoreShouldHandleNegativeScoresGracefully() {
        final String id = scoreBoardLibrary.addNewGame("home team", "away team" );
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.updateScore(id, -10, -1);
        });
    }

    @Test
    void getSummaryShouldReturnGamesInCorrectOrder() {
        final String id_1 = scoreBoardLibrary.addNewGame("Mexico", "Canada" );
        scoreBoardLibrary.updateScore(id_1, 0, 5);

        final String id_2 = scoreBoardLibrary.addNewGame("Spain", "Brazil" );
        scoreBoardLibrary.updateScore(id_2, 10, 2);

        final String id_3 = scoreBoardLibrary.addNewGame("Germany", "France" );
        scoreBoardLibrary.updateScore(id_3, 2, 2);

        final String id_4 = scoreBoardLibrary.addNewGame("Uruguay", "Italy" );
        scoreBoardLibrary.updateScore(id_4, 6, 6);

        final String id_5 = scoreBoardLibrary.addNewGame("Argentina", "Australia" );
        scoreBoardLibrary.updateScore(id_5, 3, 1);

        var collection = scoreBoardLibrary.getASummary();

        assertEquals(5, scoreBoardLibrary.getASummary().size());

        assertEquals("Uruguay", collection.get(0).getHomeTeamName());
        assertEquals("Italy", collection.get(0).getGuestTeamName());

        assertEquals("Spain", collection.get(1).getHomeTeamName());
        assertEquals("Brazil", collection.get(1).getGuestTeamName());

        assertEquals("Mexico", collection.get(2).getHomeTeamName());
        assertEquals("Canada", collection.get(2).getGuestTeamName());

        assertEquals("Argentina", collection.get(3).getHomeTeamName());
        assertEquals("Australia", collection.get(3).getGuestTeamName());

        assertEquals("Germany", collection.get(4).getHomeTeamName());
        assertEquals("France", collection.get(4).getGuestTeamName());
    }

    @Test
    void finishGameShouldRemoveMatchFromScoreboard() {
        final String id_1 = scoreBoardLibrary.addNewGame("home team 1", "away team 1" );
        final String id_2 = scoreBoardLibrary.addNewGame("home team 2", "away team 2" );
        final String id_3 = scoreBoardLibrary.addNewGame("home team 3", "away team 3" );

        scoreBoardLibrary.updateScore(id_1, 1, 1);
        scoreBoardLibrary.updateScore(id_2, 2, 2);
        scoreBoardLibrary.updateScore(id_3, 3, 3);

        scoreBoardLibrary.getScoreboard();

        scoreBoardLibrary.finishGame(id_2);

        var collection = scoreBoardLibrary.getASummary();

        assertEquals(2, scoreBoardLibrary.getASummary().size());

        assertEquals("home team 3", collection.get(0).getHomeTeamName());
        assertEquals("away team 3", collection.get(0).getGuestTeamName());

        assertEquals("home team 1", collection.get(1).getHomeTeamName());
        assertEquals("away team 1", collection.get(1).getGuestTeamName());
    }

    @Test
    void updateScoreShouldHandleMultipleMatchesSimultaneously() {
        final String id_1 = scoreBoardLibrary.addNewGame("Mexico", "Canada" );
        final String id_2 = scoreBoardLibrary.addNewGame("Spain", "Brazil" );
        final String id_3 = scoreBoardLibrary.addNewGame("Germany", "France" );
        final String id_4 = scoreBoardLibrary.addNewGame("Uruguay", "Italy" );
        final String id_5 = scoreBoardLibrary.addNewGame("Argentina", "Australia" );

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

        var collection = scoreBoardLibrary.getASummary();

        assertEquals(5, scoreBoardLibrary.getASummary().size());

        assertEquals("Spain", collection.get(0).getHomeTeamName());
        assertEquals("Brazil", collection.get(0).getGuestTeamName());

        assertEquals("Uruguay", collection.get(1).getHomeTeamName());
        assertEquals("Italy", collection.get(1).getGuestTeamName());

        assertEquals("Mexico", collection.get(2).getHomeTeamName());
        assertEquals("Canada", collection.get(2).getGuestTeamName());

        assertEquals("Argentina", collection.get(3).getHomeTeamName());
        assertEquals("Australia", collection.get(3).getGuestTeamName());

        assertEquals("Germany", collection.get(4).getHomeTeamName());
        assertEquals("France", collection.get(4).getGuestTeamName());
    }

    @Test
    void updateScoreShouldNotChangeWhenSameScoreIsGiven() {
        final String id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);

        var scoreBoard = scoreBoardLibrary.getScoreboard();
        assertEquals(1, scoreBoard.size());
        assertEquals(0, scoreBoard.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, scoreBoard.getFirst().getScore().getGuestTeamGoals());
    }

    @Test
    void addNewGameShouldThrowExceptionForIdenticalTeamNames() {
        assertThrows(IllegalArgumentException.class, () -> {
            final String id = scoreBoardLibrary.addNewGame("TeamA", "TeamA" );
        });
    }

    @Test
    void addNewGameShouldThrowExceptionForNullTeamNames() {
        assertThrows(NullPointerException.class, () -> {
            final String id = scoreBoardLibrary.addNewGame(null, "TeamB" );
        });
        assertThrows(NullPointerException.class, () -> {
            final String id = scoreBoardLibrary.addNewGame("TeamA", null);
        });
    }

    @Test
    void updateScoreShouldThrowExceptionForNegativeScores() {
        final String id = scoreBoardLibrary.addNewGame("TeamA", "TeamB" );
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
            scoreBoardLibrary.updateScore("NonExistentGameID", 1, 2);
        });
    }

    @Test
    void finishGameShouldThrowExceptionForNonExistentGame() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.finishGame("NonExistentGameID" );
        });
    }

    @Test
    void finishGameShouldThrowExceptionForNullTeamID() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreBoardLibrary.finishGame(null);
        });
    }
}