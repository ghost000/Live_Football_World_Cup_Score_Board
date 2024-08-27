package org.example.library.service;

import org.example.library.model.Game;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LiveFootballWorldCupScoreBoardLibrary {

    private final List<Game> games = new ArrayList<>();

    public void addNewGame(String homeTeamName, String guestTeamName) {
        games.add(new Game(homeTeamName, guestTeamName, System.nanoTime()));
    }

    public List<Game> getScoreboard() {
        // NOTICE
        // Returning a copy of the list (return new ArrayList<>(games);) protects the internal state of the object from unintended changes by external code.
        // Creating a new list reduces the risk of issues in a multi-threaded environment, ensuring that each thread works with its own copy of the data.
        // Encapsulation and type safety are maintained because the original collection is not directly exposed.
        return new ArrayList<>(games);
    }

    public void updateScore(String homeTeamName, int homeTeamGoals, String guestTeamName, int guestTeamGoals) {
        getGame(homeTeamName, guestTeamName).ifPresent(game1 -> game1.getScore().updateScore(homeTeamGoals, guestTeamGoals));
    }

    public void finishGame(String homeTeamName, String guestTeamName) {
        games.removeIf(game -> game.getHomeTeamName().equals(homeTeamName) && game.getGuestTeamName().equals(guestTeamName));
    }

    public List<Game> getASummary() {
        return games.stream().sorted(Comparator.comparingInt((Game game) -> game.getScore().getTotalScore()).reversed().thenComparing(Game::getStartTime, Comparator.reverseOrder())).collect(Collectors.toList());
    }

    private Optional<Game> getGame(String homeTeamName, String guestTeamName) {
        return games.stream().filter(game -> game.getHomeTeamName().equals(homeTeamName) && game.getGuestTeamName().equals(guestTeamName)).findFirst();
    }
}
