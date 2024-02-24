package org.rgoussey.manillen.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import org.rgoussey.manillen.model.Card.Suit;

public class Trick {

  @Getter
  private Suit suit;
  private final Optional<Suit> trump;
  @Getter
  private final List<Player> playerOrdering;
  Map<Player, Card> playedCards = new HashMap<>();
  @Getter
  Map<Card, Player> cardsPlayedBy = new HashMap<>();


  /**
   * @param trump              Null if no trump
   * @param playerDataOrdering The order in which the players play.
   */
  public Trick(Optional<Suit> trump, List<Player> playerDataOrdering) {
    this.trump = trump;
    this.playerOrdering = playerDataOrdering;
  }

  public void playCard(Player player, Card card) {
    if (suit == null) {
      suit = card.suit();
    }
    cardsPlayedBy.put(card, player);
    playedCards.put(player, card);
  }


  public Player getWinningPlayer() {
    Player winner = playerOrdering.getFirst();
    Card winningCard = playedCards.get(winner);
    List<Player> subList = playerOrdering.subList(1, playedCards.size());
    for (Player currentPlayer : subList) {
      Card newCard = playedCards.get(currentPlayer);
      boolean newCardIsHigher = newCard.rank().getRank() > winningCard.rank().getRank();
      boolean suitsAreEqual = newCard.suit() == winningCard.suit();
      boolean higherSameSuit = suitsAreEqual && newCardIsHigher;
      if (trump.isPresent()) {
        boolean trumpIsPlayed = newCard.suit() == trump.get();
        boolean higherTrump = trumpIsPlayed && (!suitsAreEqual || newCardIsHigher);
        if (higherTrump) {
          winner = currentPlayer;
          winningCard = newCard;
        }
      } else if (higherSameSuit) {
        winner = currentPlayer;
        winningCard = newCard;
      }
    }
    return winner;
  }

  public Team getWinningTeam() {
    return getWinningPlayer().getTeam();
  }

  public int getScore() {
    return playedCards.values().stream().mapToInt(card -> card.rank().getScore()).sum();
  }


}
