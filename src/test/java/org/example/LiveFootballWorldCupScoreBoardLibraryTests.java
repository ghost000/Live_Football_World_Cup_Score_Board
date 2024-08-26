package org.example;

import org.junit.jupiter.api.Test;

public class LiveFootballWorldCupScoreBoardLibraryTests {

    @Test
    void shouldCreateLibrary() {
        LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();
        scoreBoardLibrary.addNewGame("home team","away team");
        scoreBoardLibrary.getScoreboard();
        scoreBoardLibrary.finishGameCurrentlyInProgress();
        scoreBoardLibrary.getASummary();
    }

}
