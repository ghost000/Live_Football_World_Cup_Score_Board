package org.example.library.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.library.model.Game;
import org.example.library.model.GameSummary;
import java.util.List;

@ApplicationScoped
public final class LiveFootballWorldCupScoreBoardLibrary {

    private final GameManager gameManager;
    private final GameSummary gameSummary;

    public LiveFootballWorldCupScoreBoardLibrary() {
        this.gameManager = new LiveGameManager();
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