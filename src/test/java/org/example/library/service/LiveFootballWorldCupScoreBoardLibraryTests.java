package org.example.library.service;

import org.junit.jupiter.api.Test;

public class LiveFootballWorldCupScoreBoardLibraryTests {

    @Test
    void shouldCreateLibrary() {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();
        scoreBoardLibrary.addNewGame("home team","away team");
        scoreBoardLibrary.updateScore(0,1);
        scoreBoardLibrary.getScoreboard();
        scoreBoardLibrary.finishGameCurrentlyInProgress();
        scoreBoardLibrary.getASummary();
    }

}
