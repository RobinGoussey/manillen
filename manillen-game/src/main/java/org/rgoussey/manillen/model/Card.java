package org.rgoussey.manillen.model;

public record Card(Suit suit, Rank rank) {
    public enum Suit {
        HEARTS(0), CLUBS(1), SPADES(2), DIAMONDS(3);

        private final int number;

        Suit(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }


    public enum Rank {
        SEVEN(0, 0), EIGHT(1, 0), NINE(2, 0), JACK(3, 1), QUEEN(4, 2), KING(5, 3), ACE(6, 4), TEN(7, 5);

        private final int score;

        public int getRank() {
            return rank;
        }

        private final int rank;

        Rank(int rank, int score) {
            this.score = score;
            this.rank = rank;
        }

        public int getScore() {
            return score;
        }
    }
}
