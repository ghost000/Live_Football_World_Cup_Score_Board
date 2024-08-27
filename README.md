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