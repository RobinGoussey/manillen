package org.rgoussey.manillen.model;

import java.util.List;
import java.util.Optional;
import org.rgoussey.manillen.model.Card.Suit;

public interface PlayerStrategy {

  Card playCard(List<Card> playableCards, Round roundData, Game gameData);

  Optional<Suit> getSuit();
  //TODO shuffle moves

}
