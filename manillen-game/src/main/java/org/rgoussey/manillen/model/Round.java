package org.rgoussey.manillen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import org.rgoussey.manillen.model.Card.Suit;

/**
 * Todo, it isn't that clean that data and logic are mixed in this class. It should be split up.
 */
@Data
public class Round {

  private Optional<Suit> trump;


  private final List<Trick> tricks;


  public Round(Optional<Suit> trump) {
    this.trump = trump;
    tricks = new ArrayList<>();
  }

  public void addHand(Trick trick) {
    tricks.add(trick);
  }

  public Map<Team, Integer> getScores() {
    return tricks.stream().collect(
        Collectors.groupingBy(Trick::getWinningTeam, Collectors.summingInt(Trick::getScore)));
  }
}
