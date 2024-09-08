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
        games.add(new Game(homeTeamName, awayTeamName));
        return games.getLast().getID();
    }

    public void updateScore(int id, int homeTeamGoals, int awayTeamGoals) {
        Validator.validateScore(homeTeamGoals, "Home");
        Validator.validateScore(awayTeamGoals, "Away");

        Optional<Game> optionalGame = getGame(id);
        Game game = optionalGame.orElseThrow(() ->
                new IllegalArgumentException("Game with ID: " + id + " was not found.")
        );

        game.getScore().updateScore(homeTeamGoals, awayTeamGoals);
    }

    public void finishGame(int id) {
        Optional<Game> optionalGame = getGame(id);
        Game game = optionalGame.orElseThrow(() ->
                new IllegalArgumentException("Game with ID: " + id + " was not found.")
        );

        games.remove(game);
    }

    public List<Game> getASummary() {
        return games.stream().sorted(Comparator.comparingInt((Game game) -> game.getScore().getHomeTeamGoals() + game.getScore().getAwayTeamGoals()).reversed().thenComparing(Game::getID, Comparator.reverseOrder())).collect(Collectors.toList());
    }

    private Optional<Game> getGame(int id) {
        return games.stream().filter(game -> game.getID() == id).findFirst();
    }

    private boolean isThisTeamPlayingRightNow(String teamName) {
        return games.stream().anyMatch(game -> game.getHomeTeamName().equalsIgnoreCase(teamName)
                && game.getAwayTeamName().equalsIgnoreCase(teamName));
    }
}
