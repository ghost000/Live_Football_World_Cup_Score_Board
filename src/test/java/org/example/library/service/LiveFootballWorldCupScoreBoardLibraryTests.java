package org.example.library.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LiveFootballWorldCupScoreBoardLibraryTests {

    @Test
    void shouldCreateLibrary() {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();

        scoreBoardLibrary.addNewGame("home team","away team");
        scoreBoardLibrary.updateScore("home team", 0,"away team", 1);

        assertEquals(1, scoreBoardLibrary.getScoreboard().size());
        assertEquals(0, scoreBoardLibrary.getScoreboard().homeTeamGoals);
        assertEquals(1, scoreBoardLibrary.getScoreboard().guestTeamGoals);
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }

    @Test
    void updateScoreShouldCorrectlyUpdateOngoingMatch () {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();

        scoreBoardLibrary.addNewGame("home team","away team");
        scoreBoardLibrary.updateScore("home team", 2,"away team", 1);

        assertEquals(1, scoreBoardLibrary.getScoreboard().size());
        assertEquals(2, scoreBoardLibrary.getScoreboard().homeTeamGoals);
        assertEquals(1, scoreBoardLibrary.getScoreboard().guestTeamGoals);
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }

    @Test
    void updateScoreShouldFailForNonexistentMatch () {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();

        scoreBoardLibrary.addNewGame("home team","away team");
        assertThrows(IllegalArgumentException.class, scoreBoardLibrary.updateScore("alone team", 0,"new team", 1));
    }

    @Test
    void updateScoreShouldAllowLoweringScore () {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();

        scoreBoardLibrary.addNewGame("home team","away team");
        scoreBoardLibrary.updateScore("home team", 3,"away team", 2);
        scoreBoardLibrary.updateScore("home team", 2,"away team", 1);

        assertEquals(1, scoreBoardLibrary.getScoreboard().size());
        assertEquals(2, scoreBoardLibrary.getScoreboard().homeTeamGoals);
        assertEquals(1, scoreBoardLibrary.getScoreboard().guestTeamGoals);
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }

    @Test
    void updateScoreShouldHandleNegativeScoresGracefully () {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();
        scoreBoardLibrary.addNewGame("home team","away team");
        assertThrows(IllegalArgumentException.class, scoreBoardLibrary.updateScore("home team", -10,"away team", -1));
    }

    @Test
    void getSummaryShouldReturnGamesInCorrectOrder () {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();

        scoreBoardLibrary.addNewGame("Mexico","Canada");
        scoreBoardLibrary.addNewGame("Spain","Brazil");
        scoreBoardLibrary.addNewGame("Germany","France");
        scoreBoardLibrary.addNewGame("Uruguay","Italy");
        scoreBoardLibrary.addNewGame("Argentina","Australia");

        scoreBoardLibrary.updateScore("Mexico", 0,"Canada", 5);
        scoreBoardLibrary.updateScore("Spain", 10,"Brazil", 2);
        scoreBoardLibrary.updateScore("Germany", 2,"France", 2);
        scoreBoardLibrary.updateScore("Uruguay", 6,"Italy", 6);
        scoreBoardLibrary.updateScore("Argentina", 3,"Australia", 1);

        var collection = scoreBoardLibrary.getASummary();

        assertEquals(5, scoreBoardLibrary.getASummary().size());

        assertEquals("Uruguay", collection.get(0).homeTeamName);
        assertEquals("Italy", collection.get(0).guestTeamName);

        assertEquals("Spain", collection.get(1).homeTeamName);
        assertEquals("Brazil", collection.get(1).guestTeamName);

        assertEquals("Mexico", collection.get(2).homeTeamName);
        assertEquals("Canada", collection.get(2).guestTeamName);

        assertEquals("Argentina", collection.get(3).homeTeamName);
        assertEquals("Australia", collection.get(3).guestTeamName);

        assertEquals("Germany", collection.get(4).homeTeamName);
        assertEquals("France", collection.get(4).guestTeamName);
    }

    @Test
    void finishGameShouldRemoveMatchFromScoreboard () {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();

        scoreBoardLibrary.addNewGame("home team 1","away team 1");
        scoreBoardLibrary.addNewGame("home team 2","away team 2");
        scoreBoardLibrary.addNewGame("home team 3","away team 3");

        scoreBoardLibrary.updateScore("home team 1", 1,"away team 1", 1);
        scoreBoardLibrary.updateScore("home team 2", 2,"away team 2", 2);
        scoreBoardLibrary.updateScore("home team 3", 3,"away team 3", 3);

        scoreBoardLibrary.getScoreboard();

        scoreBoardLibrary.finishGame("home team 2","away team 2");

        var collection = scoreBoardLibrary.getASummary();

        assertEquals(2, scoreBoardLibrary.getASummary().size());

        assertEquals("home team 1", collection.get(0).homeTeamName);
        assertEquals("away team 1", collection.get(0).guestTeamName);

        assertEquals("home team 3", collection.get(1).homeTeamName);
        assertEquals("away team 3", collection.get(1).guestTeamName);

    }

    @Test
    void updateScoreShouldHandleMultipleMatchesSimultaneously () {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();
        scoreBoardLibrary.addNewGame("Mexico","Canada");
        scoreBoardLibrary.addNewGame("Spain","Brazil");
        scoreBoardLibrary.addNewGame("Germany","France");
        scoreBoardLibrary.addNewGame("Uruguay","Italy");
        scoreBoardLibrary.addNewGame("Argentina","Australia");

        scoreBoardLibrary.updateScore("Mexico", 2,"Canada", 6);
        scoreBoardLibrary.updateScore("Spain", 11,"Brazil", 3);
        scoreBoardLibrary.updateScore("Germany", 3,"France", 4);
        scoreBoardLibrary.updateScore("Uruguay", 7,"Italy", 6);
        scoreBoardLibrary.updateScore("Argentina", 4,"Australia", 5);

        scoreBoardLibrary.updateScore("Mexico", 3,"Canada", 7);
        scoreBoardLibrary.updateScore("Spain", 14,"Brazil", 4);
        scoreBoardLibrary.updateScore("Germany", 4,"France", 5);
        scoreBoardLibrary.updateScore("Uruguay", 8,"Italy", 6);
        scoreBoardLibrary.updateScore("Argentina", 5,"Australia", 5);


        scoreBoardLibrary.updateScore("Mexico", 5,"Canada", 7);
        scoreBoardLibrary.updateScore("Spain", 15,"Brazil", 7);
        scoreBoardLibrary.updateScore("Germany", 5,"France", 6);
        scoreBoardLibrary.updateScore("Uruguay", 9,"Italy", 6);
        scoreBoardLibrary.updateScore("Argentina", 6,"Australia", 5);

        var collection = scoreBoardLibrary.getASummary();

        assertEquals(5, scoreBoardLibrary.getASummary().size());

        assertEquals("Spain", collection.get(0).homeTeamName);
        assertEquals("Brazil", collection.get(0).guestTeamName);

        assertEquals("Uruguay", collection.get(1).homeTeamName);
        assertEquals("Italy", collection.get(1).guestTeamName);

        assertEquals("Mexico", collection.get(2).homeTeamName);
        assertEquals("Canada", collection.get(2).guestTeamName);

        assertEquals("Germany", collection.get(3).homeTeamName);
        assertEquals("France", collection.get(3).guestTeamName);

        assertEquals("Argentina", collection.get(4).homeTeamName);
        assertEquals("Australia", collection.get(4).guestTeamName);
    }

    @Test
    void updateScoreShouldNotChangeWhenSameScoreIsGiven () {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();
        scoreBoardLibrary.addNewGame("home team","away team");
        scoreBoardLibrary.updateScore("home team", 0,"away team", 1);
        scoreBoardLibrary.updateScore("home team", 0,"away team", 1);
        scoreBoardLibrary.updateScore("home team", 0,"away team", 1);
        scoreBoardLibrary.updateScore("home team", 0,"away team", 1);
        scoreBoardLibrary.updateScore("home team", 0,"away team", 1);
        scoreBoardLibrary.updateScore("home team", 0,"away team", 1);

        assertEquals(1, scoreBoardLibrary.getScoreboard().size());
        assertEquals(0, scoreBoardLibrary.getScoreboard().homeTeamGoals);
        assertEquals(1, scoreBoardLibrary.getScoreboard().guestTeamGoals);
        assertEquals(1, scoreBoardLibrary.getASummary().size());
    }
}
