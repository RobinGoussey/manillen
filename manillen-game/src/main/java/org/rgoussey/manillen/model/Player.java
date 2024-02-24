package org.rgoussey.manillen.model;


import java.util.Optional;
import lombok.Data;
import org.rgoussey.manillen.model.Card.Suit;

@Data
public class Player {

  private final String name;
  private final Team team;
  private PlayerStrategy playerStrategy;
  private PlayerHand playerHand;

  public Player(String name, Team team, PlayerStrategy playerStrategy) {
    this.name = name;
    this.team = team;
    this.playerStrategy = playerStrategy;
  }

  @Override
  public String toString() {
    return "Player[" +
        "name=" + name + ", " +
        "team=" + team + ", " +
        "playerStrategy=" + playerStrategy + ']';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Player player = (Player) o;

    if (!getName().equals(player.getName())) {
      return false;
    }
    return getTeam().equals(player.getTeam());
  }

  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 31 * result + getTeam().hashCode();
    return result;
  }
}
