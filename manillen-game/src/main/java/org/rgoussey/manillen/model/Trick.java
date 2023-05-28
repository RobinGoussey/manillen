package org.rgoussey.manillen.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trick {
    private final Card.Suit trump;
    private final List<PlayerData> playerDataOrdering;
    Map<PlayerData, Card> cards = new HashMap<>();

    /**
     * @param trump          Null if no trump
     * @param playerDataOrdering The order in which the players play.
     */
    public Trick(Card.Suit trump, List<PlayerData> playerDataOrdering) {
        this.trump = trump;
        this.playerDataOrdering = playerDataOrdering;
    }

    public void playCard(PlayerData playerData, Card card) {
        cards.put(playerData, card);
    }


    public Team getWinner() {
        PlayerData winner = playerDataOrdering.get(0);
        Card winningCard = cards.get(winner);
        for (PlayerData nextPlayerData : playerDataOrdering.subList(0, playerDataOrdering.size() - 1)) {
            Card newCard = cards.get(nextPlayerData);
            boolean newCardIsHigher = newCard.rank().getRank() > winningCard.rank().getRank();
            boolean suitsAreEqual = newCard.suit() == winningCard.suit();
            boolean higherSameSuit = suitsAreEqual && newCardIsHigher;
            if (trump != null) {
                boolean trumpIsPlayed = newCard.suit() == trump;
                boolean higherTrump = trumpIsPlayed && (!suitsAreEqual || newCardIsHigher);
                if (higherTrump) {
                    winner = nextPlayerData;
                    winningCard = newCard;
                }
            } else if (higherSameSuit) {
                winner = nextPlayerData;
                winningCard = newCard;
            }
        }
        return winner.team();
    }

    public int getScore() {
        return cards.values().stream().mapToInt(card -> card.rank().getScore()).sum();
    }


}
