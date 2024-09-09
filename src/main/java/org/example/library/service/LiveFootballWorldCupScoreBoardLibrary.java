package org.example.library.service;

import org.example.library.model.Game;
import org.example.library.utils.Validator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LiveFootballWorldCupScoreBoardLibrary {

    private final List<Game> games = new ArrayList<>();

    public int addNewGame(String homeTeamName, String awayTeamName) {
        Validator.validateTeamNames(homeTeamName, awayTeamName, games);
        Game newGame = new Game(homeTeamName, awayTeamName);
        games.add(newGame);
        return newGame.getID();
    }

    public void updateScore(int id, int homeTeamGoals, int awayTeamGoals) {
        Validator.validateScore(homeTeamGoals, "Home" );
        Validator.validateScore(awayTeamGoals, "Away" );

        Optional<Game> optionalGame = getGame(id);
        Game game = optionalGame.orElseThrow(() ->
                new IllegalArgumentException("Game with ID: " + id + " was not found." )
        );

        replaceGameInList(game.updateScore(homeTeamGoals, awayTeamGoals));
    }

    public void finishGame(int id) {
        Optional<Game> optionalGame = getGame(id);
        Game game = optionalGame.orElseThrow(() ->
                new IllegalArgumentException("Game with ID: " + id + " was not found." )
        );

        games.remove(game);
    }

    public List<Game> getASummary() {
        return games.stream()
                .sorted(Comparator
                        .comparingInt((Game game) -> game.getScore().homeTeamGoals() + game.getScore().awayTeamGoals())
                        .reversed()
                        .thenComparing(Game::getID, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private Optional<Game> getGame(int id) {
        return games.stream().filter(game -> game.getID() == id).findFirst();
    }

    private void replaceGameInList(Game updatedGame) {
        games.replaceAll(game -> game.getID() == updatedGame.getID() ? updatedGame : game);
    }
}