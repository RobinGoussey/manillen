package org.rgoussey.manillen.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand {
    private final Card.Suit trump;
    private final List<Player> playerOrdering;
    Map<Player, Card> cards = new HashMap<>();

    /**
     * @param trump          Null if no trump
     * @param playerOrdering The order in which the players play.
     */
    public Hand(Card.Suit trump, List<Player> playerOrdering) {
        this.trump = trump;
        this.playerOrdering = playerOrdering;
    }

    public void playCard(Player player, Card card) {
        cards.put(player, card);
    }


    public Team getWinner() {
        Player winner = playerOrdering.get(0);
        Card winningCard = cards.get(winner);
        for (Player nextPlayer : playerOrdering.subList(0, playerOrdering.size() - 1)) {
            Card newCard = cards.get(nextPlayer);
            boolean newCardIsHigher = newCard.rank().getRank() > winningCard.rank().getRank();
            boolean suitsAreEqual = newCard.suit() == winningCard.suit();
            boolean higherSameSuit = suitsAreEqual && newCardIsHigher;
            if (trump != null) {
                boolean trumpIsPlayed = newCard.suit() == trump;
                boolean higherTrump = trumpIsPlayed && (!suitsAreEqual || newCardIsHigher);
                if (higherTrump) {
                    winner = nextPlayer;
                    winningCard = newCard;
                }
            } else if (higherSameSuit) {
                winner = nextPlayer;
                winningCard = newCard;
            }
        }
        return winner.team();
    }

    public int getScore() {
        return cards.values().stream().mapToInt(card -> card.rank().getScore()).sum();
    }


}
