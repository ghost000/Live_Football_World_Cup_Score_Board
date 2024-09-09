package org.example.library.service;

import org.example.library.model.Game;
import org.example.library.model.GameSummary;
import java.util.List;

public final class LiveFootballWorldCupScoreBoardLibrary {

    private final GameManager gameManager;
    private final GameSummary gameSummary;

    public LiveFootballWorldCupScoreBoardLibrary(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameSummary = new GameSummary();
    }

    public int addNewGame(String homeTeamName, String awayTeamName) {
        return gameManager.addNewGame(homeTeamName, awayTeamName);
    }

    public void updateScore(int id, int homeTeamGoals, int awayTeamGoals) {
        gameManager.updateGameScore(id, homeTeamGoals, awayTeamGoals);
    }

    public void finishGame(int id) {
        gameManager.finishGame(id);
    }

    public List<Game> getASummary() {
        return gameSummary.generateSummary(gameManager.getGames());
    }
}