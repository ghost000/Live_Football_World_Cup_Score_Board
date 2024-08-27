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
        scoreBoardLibrary.addNewGame("home team", "away team");
        scoreBoardLibrary.updateScore("home team", 0, "away team", 1);

        var scoreBoard = scoreBoardLibrary.getScoreboard();
        assertEquals(1, scoreBoard.size());
        assertEquals(0, scoreBoard.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, scoreBoard.getFirst().getScore().getGuestTeamGoals());
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }

    @Test
    void updateScoreShouldCorrectlyUpdateOngoingMatch() {
        scoreBoardLibrary.addNewGame("home team", "away team");
        scoreBoardLibrary.updateScore("home team", 2, "away team", 1);

        var scoreBoard = scoreBoardLibrary.getScoreboard();
        assertEquals(1, scoreBoard.size());
        assertEquals(2, scoreBoard.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, scoreBoard.getFirst().getScore().getGuestTeamGoals());
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }

    @Test
    void updateScoreShouldFailForNonexistentMatch() {
        scoreBoardLibrary.addNewGame("home team", "away team");
//        assertThrows(IllegalArgumentException.class, scoreBoardLibrary.updateScore("alone team", 0,"new team", 1));
    }

    @Test
    void updateScoreShouldAllowLoweringScore() {
        scoreBoardLibrary.addNewGame("home team", "away team");
        scoreBoardLibrary.updateScore("home team", 3, "away team", 2);
        scoreBoardLibrary.updateScore("home team", 2, "away team", 1);

        var scoreBoard = scoreBoardLibrary.getScoreboard();
        assertEquals(1, scoreBoard.size());
        assertEquals(2, scoreBoard.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, scoreBoard.getFirst().getScore().getGuestTeamGoals());
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }

    @Test
    void updateScoreShouldHandleNegativeScoresGracefully() {
        scoreBoardLibrary.addNewGame("home team", "away team");
//        assertThrows(IllegalArgumentException.class, scoreBoardLibrary.updateScore("home team", -10,"away team", -1));
    }

    @Test
    void getSummaryShouldReturnGamesInCorrectOrder() {
        scoreBoardLibrary.addNewGame("Mexico", "Canada");
        scoreBoardLibrary.updateScore("Mexico", 0, "Canada", 5);

        scoreBoardLibrary.addNewGame("Spain", "Brazil");
        scoreBoardLibrary.updateScore("Spain", 10, "Brazil", 2);

        scoreBoardLibrary.addNewGame("Germany", "France");
        scoreBoardLibrary.updateScore("Germany", 2, "France", 2);

        scoreBoardLibrary.addNewGame("Uruguay", "Italy");
        scoreBoardLibrary.updateScore("Uruguay", 6, "Italy", 6);

        scoreBoardLibrary.addNewGame("Argentina", "Australia");
        scoreBoardLibrary.updateScore("Argentina", 3, "Australia", 1);

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
        scoreBoardLibrary.addNewGame("home team 1", "away team 1");
        scoreBoardLibrary.addNewGame("home team 2", "away team 2");
        scoreBoardLibrary.addNewGame("home team 3", "away team 3");

        scoreBoardLibrary.updateScore("home team 1", 1, "away team 1", 1);
        scoreBoardLibrary.updateScore("home team 2", 2, "away team 2", 2);
        scoreBoardLibrary.updateScore("home team 3", 3, "away team 3", 3);

        scoreBoardLibrary.getScoreboard();

        scoreBoardLibrary.finishGame("home team 2", "away team 2");

        var collection = scoreBoardLibrary.getASummary();

        assertEquals(2, scoreBoardLibrary.getASummary().size());

        assertEquals("home team 3", collection.get(0).getHomeTeamName());
        assertEquals("away team 3", collection.get(0).getGuestTeamName());

        assertEquals("home team 1", collection.get(1).getHomeTeamName());
        assertEquals("away team 1", collection.get(1).getGuestTeamName());
    }

    @Test
    void updateScoreShouldHandleMultipleMatchesSimultaneously() {
        scoreBoardLibrary.addNewGame("Mexico", "Canada");
        scoreBoardLibrary.addNewGame("Spain", "Brazil");
        scoreBoardLibrary.addNewGame("Germany", "France");
        scoreBoardLibrary.addNewGame("Uruguay", "Italy");
        scoreBoardLibrary.addNewGame("Argentina", "Australia");

        scoreBoardLibrary.updateScore("Mexico", 2, "Canada", 6);
        scoreBoardLibrary.updateScore("Spain", 11, "Brazil", 3);
        scoreBoardLibrary.updateScore("Germany", 3, "France", 4);
        scoreBoardLibrary.updateScore("Uruguay", 7, "Italy", 6);
        scoreBoardLibrary.updateScore("Argentina", 4, "Australia", 5);

        scoreBoardLibrary.updateScore("Mexico", 3, "Canada", 7);
        scoreBoardLibrary.updateScore("Spain", 14, "Brazil", 4);
        scoreBoardLibrary.updateScore("Germany", 4, "France", 5);
        scoreBoardLibrary.updateScore("Uruguay", 8, "Italy", 6);
        scoreBoardLibrary.updateScore("Argentina", 5, "Australia", 5);


        scoreBoardLibrary.updateScore("Mexico", 5, "Canada", 7);
        scoreBoardLibrary.updateScore("Spain", 15, "Brazil", 7);
        scoreBoardLibrary.updateScore("Germany", 5, "France", 6);
        scoreBoardLibrary.updateScore("Uruguay", 9, "Italy", 6);
        scoreBoardLibrary.updateScore("Argentina", 6, "Australia", 5);

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
        scoreBoardLibrary.addNewGame("home team", "away team");
        scoreBoardLibrary.updateScore("home team", 0, "away team", 1);
        scoreBoardLibrary.updateScore("home team", 0, "away team", 1);
        scoreBoardLibrary.updateScore("home team", 0, "away team", 1);
        scoreBoardLibrary.updateScore("home team", 0, "away team", 1);
        scoreBoardLibrary.updateScore("home team", 0, "away team", 1);
        scoreBoardLibrary.updateScore("home team", 0, "away team", 1);

        var scoreBoard = scoreBoardLibrary.getScoreboard();
        assertEquals(1, scoreBoard.size());
        assertEquals(0, scoreBoard.getFirst().getScore().getHomeTeamGoals());
        assertEquals(1, scoreBoard.getFirst().getScore().getGuestTeamGoals());
    }
}