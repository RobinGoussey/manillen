package org.rgoussey.manillen.model;

import com.sun.jdi.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StartInfo {

    private StartInfo() {
    }

    private static Random random = new Random();

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

    public static List<PlayerHand> getPlayerCards(List<Card> deck, List<PlayerData> playerData) {
        // Device, each player gets 3 cards, then each gets 2 cards, then each gets 3 cards
        List<PlayerHand> playerHands = new ArrayList<>();
        List<Integer> cardShuffleOrder = List.of(3, 2, 3);
        List<List<Card>> cardsPerPlayer = new ArrayList<>();
        int cardIndex = 0;
        for (Integer integer : cardShuffleOrder) {
            for (PlayerData playerDatum : playerData) {
                if (cardsPerPlayer.size() < playerData.size()) {
                    cardsPerPlayer.add(new ArrayList<>());
                }
                cardsPerPlayer.get(playerData.indexOf(playerDatum)).addAll(deck.subList(cardIndex, cardIndex + integer));
                cardIndex += integer;
            }
        }
        for (PlayerData playerDatum : playerData) {
            playerHands.add(new PlayerHand(playerDatum, cardsPerPlayer.get(playerData.indexOf(playerDatum))));
        }
        return playerHands;
    }
}
