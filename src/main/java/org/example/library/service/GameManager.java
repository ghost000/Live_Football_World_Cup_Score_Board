package org.example.library.service;

import org.example.library.model.Game;

import java.util.List;

public interface GameManager {
    int addNewGame(String homeTeamName, String awayTeamName);
    void updateGameScore(int id, int homeTeamGoals, int awayTeamGoals);
    void finishGame(int id);
    List<Game> getGames();
}
