package org.rgoussey.manillen.model;

import java.util.ArrayList;
import java.util.List;

public class Round {

    private final List<Hand> hands;


    public Round() {
        hands = new ArrayList<>();
    }

    public void addHand(Hand hand) {
        hands.add(hand);
    }

    public List<Hand> getHands() {
        return hands;
    }

}
