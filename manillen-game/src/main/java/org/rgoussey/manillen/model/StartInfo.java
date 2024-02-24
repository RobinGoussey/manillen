package org.rgoussey.manillen.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StartInfo {

  private StartInfo() {
  }

  private static final Random random = new Random();

  public static List<Card> getDeck() {
    List<Card> cards = new ArrayList<>();
    for (Card.Suit suit : Card.Suit.values()) {
      for (Card.Rank rank : Card.Rank.values()) {
        cards.add(new Card(suit, rank));
      }
    }
    return cards;
  }

  public static List<Card> getShuffledDeck(List<Card> deck) {
    List<Card> shuffledDeck = new ArrayList<>(deck);
    for (int i = 0; i < shuffledDeck.size(); i++) {
      Card card = shuffledDeck.get(i);
      int randomIndex = random.nextInt(shuffledDeck.size() - i);
      shuffledDeck.set(i, shuffledDeck.get(randomIndex));
      shuffledDeck.set(randomIndex, card);
    }
    return shuffledDeck;
  }

  public static void givePlayersANewHand(List<Card> deck,
      List<Player> playerData) {
    // Device, each player gets 3 playedCards, then each gets 2 playedCards, then each gets 3 playedCards
    List<Integer> cardShuffleOrder = List.of(3, 2, 3);
    List<List<Card>> cardsPerPlayer = new ArrayList<>();
    int cardIndex = 0;
    for (Integer integer : cardShuffleOrder) {
      for (Player playerDatum : playerData) {
        if (cardsPerPlayer.size() < playerData.size()) {
          cardsPerPlayer.add(new ArrayList<>());
        }
        cardsPerPlayer.get(playerData.indexOf(playerDatum))
            .addAll(deck.subList(cardIndex, cardIndex + integer));
        cardIndex += integer;
      }
    }
    for (Player player : playerData) {
      PlayerHand playerHand = new PlayerHand(cardsPerPlayer.get(playerData.indexOf(player)));
      player.setPlayerHand(playerHand);
    }
  }
}
