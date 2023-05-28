package org.rgoussey.manillen.model;

import java.util.List;

public record PlayerHand(

        PlayerData playerData,
        List<Card> remainingCards) {

    public void playCard(Card card) {
        remainingCards.remove(card);
    }
}
