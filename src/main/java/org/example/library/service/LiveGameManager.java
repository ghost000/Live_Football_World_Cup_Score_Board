package org.example.library.service;

import org.example.library.model.Game;
import org.example.library.utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class LiveGameManager implements GameManager {
    private final List<Game> games = new ArrayList<>();

    @Override
    public int addNewGame(String homeTeamName, String awayTeamName) {
        Validator.validateTeamNames(homeTeamName, awayTeamName, games);
        Game newGame = new Game(homeTeamName, awayTeamName);
        games.add(newGame);
        return newGame.getID();
    }

    @Override
    public void updateGameScore(int id, int homeTeamGoals, int awayTeamGoals) {
        Optional<Game> optionalGame = findGameById(id);
        Game game = optionalGame.orElseThrow(() ->
                new IllegalArgumentException("Game with ID: " + id + " was not found."));
        replaceGameInList(game.updateScore(homeTeamGoals, awayTeamGoals));
    }

    @Override
    public void finishGame(int id) {
        Optional<Game> optionalGame = findGameById(id);
        Game game = optionalGame.orElseThrow(() ->
                new IllegalArgumentException("Game with ID: " + id + " was not found."));
        games.remove(game);
    }

    @Override
    public List<Game> getGames() {
        return games;
    }

    private void replaceGameInList(Game updatedGame) {
        games.replaceAll(game -> game.getID() == updatedGame.getID() ? updatedGame : game);
    }

    private Optional<Game> findGameById(int id) {
        return games.stream().filter(game -> game.getID() == id).findFirst();
    }
}
