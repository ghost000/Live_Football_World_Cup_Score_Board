# Live Football World Cup Score Board

![Build Status](https://github.com/ghost000/Live_Football_World_Cup_Score_Board/actions/workflows/java-ci.yml/badge.svg)

## Overview

**Live Football World Cup Score Board** is a library designed to manage and display live scores for ongoing football matches during the World Cup. It provides a simple interface to start new games, update scores, and generate a summary of matches ordered by their total score.

## Features

- Start a new game with an initial score of 0-0.
- Update the score with the current goals for both teams.
- Finish a game and remove it from the scoreboard.
- Retrieve a summary of all ongoing games, sorted by total score and start time.

## ToDo
- [ ] (code) validation
  -    params validation ( == null, .isBlank(), < 0, Objects.requireNonNull, Optional.ofNullable => IllegalArgumentException("ERROR")
- [x] (test) @BeforeEach void setUp() { new } 
- [x] (code) find object.values().stream().filter(object -> object.filter().equalsIgnoreCase(filter)).collect(Collectors.toList())