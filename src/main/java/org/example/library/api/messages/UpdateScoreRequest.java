package org.example.library.api.messages;

public record UpdateScoreRequest(int homeTeamGoals, int awayTeamGoals) {
}