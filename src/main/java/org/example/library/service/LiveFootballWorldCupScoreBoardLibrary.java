package org.example.library.service;

import org.example.library.model.Game;
import org.example.library.utils.Validator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.UUID;

public class LiveFootballWorldCupScoreBoardLibrary {

    private final List<Game> games = new ArrayList<>();

    public String addNewGame(String homeTeamName, String guestTeamName) {
        Validator.validateTeamNames(homeTeamName, guestTeamName);
        final String id = UUID.randomUUID().toString();
        games.add(new Game(homeTeamName, guestTeamName, System.nanoTime(), id));
        return id;
    }

    public void updateScore(String id, int homeTeamGoals, int guestTeamGoals) {
        Validator.validateScore(homeTeamGoals, "Home");
        Validator.validateScore(guestTeamGoals, "Guest");

        Optional<Game> optionalGame = getGame(id);
        Game game = optionalGame.orElseThrow(() ->
                new IllegalArgumentException("Game with ID: " + id + " was not found.")
        );

        game.getScore().updateScore(homeTeamGoals, guestTeamGoals);
    }

    public void finishGame(String id) {
        Optional<Game> optionalGame = getGame(id);
        Game game = optionalGame.orElseThrow(() ->
                new IllegalArgumentException("Game with ID: " + id + " was not found.")
        );

        games.remove(game);
    }

    public List<Game> getASummary() {
        return games.stream().sorted(Comparator.comparingInt((Game game) -> game.getScore().getHomeTeamGoals() + game.getScore().getGuestTeamGoals()).reversed().thenComparing(Game::getStartTime, Comparator.reverseOrder())).collect(Collectors.toList());
    }

    private Optional<Game> getGame(String id) {
        return games.stream().filter(game -> game.getID().equals(id)).findFirst();
    }
}
