# Live Football World Cup Score Board

![Build Status](https://github.com/ghost000/Live_Football_World_Cup_Score_Board/actions/workflows/java-ci.yml/badge.svg)

## Overview

**Live Football World Cup Score Board** is a library designed to manage and display live scores for ongoing football matches during the World Cup. It provides a simple interface to start new games, update scores, and generate a summary of matches ordered by their total score.

## Features

- Start a new game with an initial score of 0-0.
- Update the score with the current goals for both teams.
- Finish a game and remove it from the scoreboard.
- Retrieve a summary of all ongoing games, sorted by total score and start time.

## Setup

Requirements:
- Java 22
- Maven
- internet connection

To clean compiles the source code, runs the tests and install run 

```bash
mvn clean install
```

## Usage

```java
import org.example.library.service;

public class Main {
    public static void main(String[] args) {
      LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary = new LiveFootballWorldCupScoreBoardLibrary();

        // Start a new match
        scoreBoardLibrary.startMatch("home team", "away team");

        // Update the score
        scoreBoardLibrary.updateScore("home team", "away team", 1, 1);

        // Get a summary of matches
        List<Game> summary = scoreBoardLibrary.getASummary();

        // Finish the match
        scoreBoardLibrary.finishGame("home team", "away team");
    }
}
```
## Tests

Command for run unit tests

```bash
mvn test
```

## Installation
Add bellow code to `pom.xml`

```xml
<dependencies>
    <dependency>
        <groupId>org.example</groupId>
        <artifactId>Live_Football_World_Cup_Score_Board</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```


## License
The MIT License (MIT)

## ToDo
- [ ] Add logging system
- [ ] Add support for multithreading
- [ ] Add better error / exception handling
- [ ] Add JavaDoc documentation
- [ ] Add static code analysis
- [ ] Add profiling tests / analysis
- [ ] Think about performance / scalability / persistence (DB)

## Tasks / comments from review
- [x] Redundant Score constructor.
- [X] Code is quite procedural, could be more maintainable and expressive using OO.
- [ ] Lacks use of design patterns.
- [x] Score class is mutable whereas Game is immutable. Seems inconsistent. 
- [x] getTotalScore() doesn't really belong on Score as is only ever used by the sorting logic and can be calculated there.
- [x] Cumbersome API which requires passing of team names, when addNewGame() could have returned a match ID which other methods could have used.
- [X] LiveFootballWorldCupScoreBoardLibrary.getScoreboard() is only ever used in tests.
- [X] Use of System.nanoTime() to store insert order on match isn't ideal. It's possible to add matches so fast that they share the same timestamp. Use of a simple AtomicInteger would have made this more robust.
- [X] Validator.validateTeamName() is public even though it's not used externally.
- [X] Validator.validateNotNull() is only ever used in tests.
- [X] Validator.validateTeamNames() fails when comparing "England" against "England ".
- [X] Lacks validation. It's possible to start a game with team names containing whitespace, and even start a match with a team which is already playing.
- [X] Unusual naming e.g. getGuestTeamGoals() instead of Away, shouldCreateLibrary() test method which creates a scoreboard and not a library.
- [X] Too many asserts in some test scenarios.
- [X] Unit test methods are far too big and unmaintainable, too many asserts in each test method.
- [X] Test updateScoreShouldHandleMultipleMatchesSimultaneously() doesn't update matches in parallel, does so sequentially. NOTE: to discuss 