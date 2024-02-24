package org.rgoussey.manillen.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.rgoussey.manillen.model.Card.Suit;
import org.slf4j.Logger;

import static org.rgoussey.manillen.model.StartInfo.getDeck;
import static org.rgoussey.manillen.model.StartInfo.getShuffledDeck;
import static org.slf4j.LoggerFactory.getLogger;

public class Game {

  private static final Logger LOGGER = getLogger(Game.class);
  List<Player> players;
  List<Round> rounds;

  Map<Team, Integer> scores = new HashMap<>();


  public Game(List<Player> players) {
    this.players = players;
    rounds = new ArrayList<>();
  }

  public Team play() {
    LOGGER.info("Starting game with players {}", players);
    while (scores.values().stream().noneMatch(score -> score > 101)) {
      LOGGER.info("Starting round");
      Round round = playRound();
      round.getScores().entrySet().stream().filter(entry -> entry.getValue() > 30)
          .forEach(entry -> {
            //TODO special rules of doubling.
            scores.put(entry.getKey(), scores.getOrDefault(entry.getKey(), 0) + entry.getValue());
          });
    }
    return scores.entrySet().stream().filter(entry -> entry.getValue() > 101).findFirst().get()
        .getKey();


  }

  private Round playRound() {
    givePlayersANewHand();
    Player firstPlayer = players.getFirst();//TODO whoever shuffles
    Round round = new Round(firstPlayer.getPlayerStrategy().getSuit());
    LOGGER.info("Starting round with first player {}, has chosen trump {}", firstPlayer.getName(),
        round.getTrump().map(Enum::name).orElse("none"));
    for (int i = 0; i < 8; i++) {
      Trick trick = playTrick(round, firstPlayer);
      round.addHand(trick);
      firstPlayer = trick.getWinningPlayer();
      LOGGER.info("Player {} won trick", firstPlayer.getName());
    }
    return round;
  }

  private Trick playTrick(Round round, Player startingPlayer) {
    int indexOfStartingPlayer = players.indexOf(startingPlayer);
    Trick trick = new Trick(round.getTrump(), players); //TODO order of players in list
    LOGGER.info("Starting trick with first player {}", startingPlayer.getName());
    for (int i = 0; i < players.size(); i++) {
      Player currentPlayer = players.get((indexOfStartingPlayer + i) % players.size());
      List<Card> playableCards = getPlayableCards(currentPlayer,
          trick, round);
      Card card = currentPlayer.getPlayerStrategy().playCard(playableCards, round, this);
      currentPlayer.getPlayerHand().playCard(card);
      LOGGER.info("Player {} played card {}", currentPlayer.getName(), card);
      trick.playCard(currentPlayer, card);
    }
    return trick;
  }

  private List<Card> getPlayableCards(Player player, Trick trick, Round round) {
    List<Card> allCardsInHand = player.getPlayerHand().remainingCards();
    boolean noCardsHaveBeenPlayedYet = trick.playedCards.isEmpty();
    if (noCardsHaveBeenPlayedYet) {
      return allCardsInHand;
    }
    Suit suitToFollow = trick.getSuit();
    List<Card> cardsThatFollowSuit = getCardsThatFollowSuit(allCardsInHand,
        suitToFollow);
    if (!cardsThatFollowSuit.isEmpty()) {
      return cardsThatFollowSuit;
    }
    var winningTeam = trick.getWinningPlayer().getTeam();
    boolean myBuddyIsWinning = winningTeam.equals(player.getTeam());
    if (myBuddyIsWinning) {
      return allCardsInHand;
    }
    if (round.getTrump().isPresent()) {
      List<Card> trumpCards = getCardsThatFollowSuit(allCardsInHand,
          round.getTrump().get());
      if (!trumpCards.isEmpty()) {
        return trumpCards;
      }
    }
    return allCardsInHand;
  }

  private static List<Card> getCardsThatFollowSuit(List<Card> allCardsInHand, Suit suitToFollow) {
    List<Card> cardsThatFollowSuit = allCardsInHand.stream()
        .filter(card -> Objects.equals(suitToFollow, card.suit()))
        .toList();
    return cardsThatFollowSuit;
  }


  public void givePlayersANewHand() {
    StartInfo.givePlayersANewHand(getShuffledDeck(getDeck()), players);
  }

  public Map<Team, Integer> getScores() {
    return scores;
  }
}
