package org.rgoussey.manillen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.rgoussey.manillen.model.StartInfo.getDeck;
import static org.rgoussey.manillen.model.StartInfo.getPlayerCards;
import static org.rgoussey.manillen.model.StartInfo.getShuffledDeck;

public class Game {

    private final Map<Team, PlayerData> players;
    List<Round> rounds = new ArrayList<>();

    public Game(Map<Team, PlayerData> players) {
        this.players = players;
    }

    public void startGame() {
        Round round = new Round();

    }


    public List<PlayerHand> getCards() {
        List<Card> deck = getShuffledDeck(getDeck());
        return getPlayerCards(deck, new ArrayList<>(players.values()));
    }
}
