package org.rgoussey.manillen.model;

import java.util.ArrayList;
import java.util.List;

public class Round {

    private final List<Trick> tricks;


    public Round() {
        tricks = new ArrayList<>();
    }

    public void addHand(Trick trick) {
        tricks.add(trick);
    }

    public List<Trick> getHands() {
        return tricks;
    }

}
