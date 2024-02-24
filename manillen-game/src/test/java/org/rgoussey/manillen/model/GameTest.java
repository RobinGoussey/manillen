package org.rgoussey.manillen.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

class GameTest {

  private static final Logger LOGGER = getLogger(GameTest.class);

  @Test
    //This is a test to check game flow
  void playGame() {
    Team team1 = new Team("Team1");
    Team team2 = new Team("Team2");

    List<Player> players = List.of(
        new Player("Player1", team1, new RandomStrategy()),
        new Player("Player2", team2, new RandomStrategy()),
        new Player("Player3", team1, new RandomStrategy()),
        new Player("Player4", team2, new RandomStrategy())
    );
    Game game = new Game(players);
    Team teamThatWon = game.play();
    LOGGER.info("Team {} won, with a score of {}", teamThatWon, game.getScores().get(teamThatWon) );
    LOGGER.info("Scores: {}", game.getScores());
  }

}
