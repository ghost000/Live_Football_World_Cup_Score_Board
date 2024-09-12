package org.example.library.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class GameSummary {

    public List<Game> generateSummary(List<Game> games) {
        return games.stream()
                .sorted(Comparator
                        .comparingInt((Game game) -> game.getScore().homeTeamGoals() + game.getScore().awayTeamGoals())
                        .reversed()
                        .thenComparing(Game::getID, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}