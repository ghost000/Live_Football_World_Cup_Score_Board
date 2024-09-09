package org.example.library.service;

import org.example.library.model.GameSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LiveFootballWorldCupScoreBoardLibraryTests {
    private LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary;

    @BeforeEach
    void setUp() {
        scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary(new LiveGameManager());
    }

    @Test
    void shouldCreateScoreBoard() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 0, 1);

        var summary = scoreBoardLibrary.getASummary();
        assertEquals(1, summary.size());
        assertEquals(0, summary.getFirst().getScore().homeTeamGoals());
        assertEquals(1, summary.getFirst().getScore().awayTeamGoals());
    }

    @Test
    void updateScoreShouldCorrectlyUpdateOngoingMatch() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 2, 1);

        var summary = scoreBoardLibrary.getASummary();
        assertEquals(1, summary.size());
        assertEquals(3, summary.getFirst().getScore().homeTeamGoals()
                + summary.getFirst().getScore().awayTeamGoals());
    }

    @Test
    void updateScoreShouldFailForNonexistentMatch() {
        assertThrows(IllegalArgumentException.class, () -> scoreBoardLibrary.updateScore(1234567890, 0, 1));
    }

    @Test
    void updateScoreShouldAllowLoweringScore() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 3, 2);
        scoreBoardLibrary.updateScore(id, 2, 1);

        var summary = scoreBoardLibrary.getASummary();
        assertEquals(1, summary.size());
        assertEquals(3, summary.getFirst().getScore().homeTeamGoals()
                + summary.getFirst().getScore().awayTeamGoals());
    }

    @Test
    void updateScoreShouldHandleNegativeScoresGracefully() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        assertThrows(IllegalArgumentException.class, () -> scoreBoardLibrary.updateScore(id, -10, -1));
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

        assertEquals(5, summary.size());

        List<String[]> expectedTeams = List.of(
                new String[]{"Uruguay", "Italy"},
                new String[]{"Spain", "Brazil"},
                new String[]{"Mexico", "Canada"},
                new String[]{"Argentina", "Australia"},
                new String[]{"Germany", "France"}
        );

        for (int i = 0; i < expectedTeams.size(); i++) {
            assertEquals(expectedTeams.get(i)[0], summary.get(i).getHomeTeamName());
            assertEquals(expectedTeams.get(i)[1], summary.get(i).getAwayTeamName());
        }
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

        List<String[]> expectedTeams = List.of(
                new String[]{"home team 3", "away team 3"},
                new String[]{"home team 1", "away team 1"}
        );

        for (int i = 0; i < expectedTeams.size(); i++) {
            assertEquals(expectedTeams.get(i)[0], summary.get(i).getHomeTeamName());
            assertEquals(expectedTeams.get(i)[1], summary.get(i).getAwayTeamName());
        }
    }

    @Test
    void updateScoreShouldHandleMultipleMatchesSequentially() {
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

        List<String[]> expectedTeams = List.of(
                new String[]{"Spain", "Brazil"},
                new String[]{"Uruguay", "Italy"},
                new String[]{"Mexico", "Canada"},
                new String[]{"Argentina", "Australia"},
                new String[]{"Germany", "France"}
        );

        for (int i = 0; i < expectedTeams.size(); i++) {
            assertEquals(expectedTeams.get(i)[0], summary.get(i).getHomeTeamName());
            assertEquals(expectedTeams.get(i)[1], summary.get(i).getAwayTeamName());
        }
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

        List<String[]> expectedTeams = List.of(
                new String[]{"Spain", "Brazil"},
                new String[]{"Uruguay", "Italy"},
                new String[]{"Mexico", "Canada"},
                new String[]{"Argentina", "Australia"},
                new String[]{"Germany", "France"}
        );

        for (int i = 0; i < expectedTeams.size(); i++) {
            assertEquals(expectedTeams.get(i)[0], summary.get(i).getHomeTeamName());
            assertEquals(expectedTeams.get(i)[1], summary.get(i).getAwayTeamName());
        }
    }
//NOTE: non-deterministic behavior
//    @Test
//    void updateScoreShouldHandleMultipleMatchesSimultaneouslyViaParallelStream() throws InterruptedException {
//        final int id_1 = scoreBoardLibrary.addNewGame("Mexico", "Canada" );
//        final int id_2 = scoreBoardLibrary.addNewGame("Spain", "Brazil" );
//        final int id_3 = scoreBoardLibrary.addNewGame("Germany", "France" );
//        final int id_4 = scoreBoardLibrary.addNewGame("Uruguay", "Italy" );
//        final int id_5 = scoreBoardLibrary.addNewGame("Argentina", "Australia" );
//
//// NOTE: non-deterministic behavior
////        List<Runnable> tasks = List.of(
////                () -> scoreBoardLibrary.updateScore(id_1, 2, 6),
////                () -> scoreBoardLibrary.updateScore(id_2, 11, 3),
////                () -> scoreBoardLibrary.updateScore(id_3, 3, 4),
////                () -> scoreBoardLibrary.updateScore(id_4, 7, 6),
////                () -> scoreBoardLibrary.updateScore(id_5, 4, 5),
////
////                () -> scoreBoardLibrary.updateScore(id_1, 3, 7),
////                () -> scoreBoardLibrary.updateScore(id_2, 14, 4),
////                () -> scoreBoardLibrary.updateScore(id_3, 4, 5),
////                () -> scoreBoardLibrary.updateScore(id_4, 8, 6),
////                () -> scoreBoardLibrary.updateScore(id_5, 5, 5),
////
////                () -> scoreBoardLibrary.updateScore(id_1, 5, 7),
////                () -> scoreBoardLibrary.updateScore(id_2, 15, 7),
////                () -> scoreBoardLibrary.updateScore(id_3, 5, 6),
////                () -> scoreBoardLibrary.updateScore(id_4, 9, 6),
////                () -> scoreBoardLibrary.updateScore(id_5, 6, 5)
////        );
//
//        int numberOfTasks = 15; // Number of tasks
//        CountDownLatch latch = new CountDownLatch(numberOfTasks);
//
//        List<Runnable> tasks = List.of(
//                () -> {
//                    scoreBoardLibrary.updateScore(id_1, 2, 6);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_2, 11, 3);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_3, 3, 4);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_4, 7, 6);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_5, 4, 5);
//                    latch.countDown();
//                },
//
//                () -> {
//                    scoreBoardLibrary.updateScore(id_1, 3, 7);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_2, 14, 4);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_3, 4, 5);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_4, 8, 6);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_5, 5, 5);
//                    latch.countDown();
//                },
//
//                () -> {
//                    scoreBoardLibrary.updateScore(id_1, 5, 7);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_2, 15, 7);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_3, 5, 6);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_4, 9, 6);
//                    latch.countDown();
//                },
//                () -> {
//                    scoreBoardLibrary.updateScore(id_5, 6, 5);
//                    latch.countDown();
//                }
//        );
//
//        tasks.parallelStream().forEach(Runnable::run);
//
//        var summary = scoreBoardLibrary.getASummary();
//
//        assertEquals(5, summary.size());
//
//        List<String[]> expectedTeams = List.of(
//                new String[]{"Spain", "Brazil"},
//                new String[]{"Uruguay", "Italy"},
//                new String[]{"Mexico", "Canada"},
//                new String[]{"Argentina", "Australia"},
//                new String[]{"Germany", "France"}
//        );
//
//        for (int i = 0; i < expectedTeams.size(); i++) {
//            assertEquals(expectedTeams.get(i)[0], summary.get(i).getHomeTeamName());
//            assertEquals(expectedTeams.get(i)[1], summary.get(i).getAwayTeamName());
//        }
//    }

    @Test
    void updateScoreShouldHandleMultipleMatchesSimultaneouslyViaExecutorService() throws InterruptedException {
        final int[] ids = new int[5];

        try (ExecutorService executor = Executors.newFixedThreadPool(5)) {

            executor.submit(() -> ids[0] = scoreBoardLibrary.addNewGame("Mexico", "Canada" ));
            executor.submit(() -> ids[1] = scoreBoardLibrary.addNewGame("Spain", "Brazil" ));
            executor.submit(() -> ids[2] = scoreBoardLibrary.addNewGame("Germany", "France" ));
            executor.submit(() -> ids[3] = scoreBoardLibrary.addNewGame("Uruguay", "Italy" ));
            executor.submit(() -> ids[4] = scoreBoardLibrary.addNewGame("Argentina", "Australia" ));

            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        }

        try (ExecutorService executor = Executors.newFixedThreadPool(5)) {
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[0], 2, 6));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[1], 11, 3));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[2], 3, 4));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[3], 7, 6));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[4], 4, 5));

            executor.submit(() -> scoreBoardLibrary.updateScore(ids[0], 3, 7));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[1], 14, 4));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[2], 4, 5));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[3], 8, 6));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[4], 5, 5));

            executor.submit(() -> scoreBoardLibrary.updateScore(ids[0], 5, 7));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[1], 15, 7));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[2], 5, 6));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[3], 9, 6));
            executor.submit(() -> scoreBoardLibrary.updateScore(ids[4], 6, 5));

            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        }

        var summary = scoreBoardLibrary.getASummary();

        assertEquals(5, summary.size());

        List<String[]> expectedTeams = List.of(
                new String[]{"Spain", "Brazil"},
                new String[]{"Uruguay", "Italy"},
                new String[]{"Mexico", "Canada"},
                new String[]{"Argentina", "Australia"},
                new String[]{"Germany", "France"}
        );

        for (int i = 0; i < expectedTeams.size(); i++) {
            assertEquals(expectedTeams.get(i)[0], summary.get(i).getHomeTeamName());
            assertEquals(expectedTeams.get(i)[1], summary.get(i).getAwayTeamName());
        }
    }

    @Test
    void updateScoreShouldNotChangeWhenSameScoreIsGiven() {
        final int id = scoreBoardLibrary.addNewGame("home team", "away team" );
        scoreBoardLibrary.updateScore(id, 0, 1);
        scoreBoardLibrary.updateScore(id, 0, 1);

        var summary = scoreBoardLibrary.getASummary();
        assertEquals(1, summary.size());
        assertEquals(1, summary.getFirst().getScore().homeTeamGoals() + summary.getFirst().getScore().awayTeamGoals());
    }

    @Test
    void addNewGameShouldThrowExceptionForIdenticalTeamNames() {
        assertThrows(IllegalArgumentException.class, () -> scoreBoardLibrary.addNewGame("TeamA", "TeamA" ));
    }

    @Test
    void addNewGameShouldThrowExceptionForNullTeamNames() {
        assertThrows(NullPointerException.class, () -> scoreBoardLibrary.addNewGame(null, "TeamB" ));
        assertThrows(NullPointerException.class, () -> scoreBoardLibrary.addNewGame("TeamA", null));
    }

    @Test
    void updateScoreShouldThrowExceptionForNegativeScores() {
        final int id = scoreBoardLibrary.addNewGame("TeamA", "TeamB" );
        assertThrows(IllegalArgumentException.class, () -> scoreBoardLibrary.updateScore(id, -1, 3));
        assertThrows(IllegalArgumentException.class, () -> scoreBoardLibrary.updateScore(id, 2, -3));
    }

    @Test
    void updateScoreShouldThrowExceptionForNonExistentGame() {
        assertThrows(IllegalArgumentException.class, () -> scoreBoardLibrary.updateScore(1234567890, 1, 2));
    }

    @Test
    void finishGameShouldThrowExceptionForNonExistentGame() {
        assertThrows(IllegalArgumentException.class, () -> scoreBoardLibrary.finishGame(1234567890));
    }
}