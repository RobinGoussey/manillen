package org.rgoussey.manillen.model;

import java.util.List;
import java.util.Optional;
import org.rgoussey.manillen.model.Card.Suit;

public class RandomStrategy implements PlayerStrategy {

  @Override
  public Card playCard(List<Card> playableCards, Round roundData, Game gameData) {
    return playableCards.get(0);
  }

  @Override
  public Optional<Suit> getSuit() {
    return Optional.of(Suit.DIAMONDS);
  }
}
