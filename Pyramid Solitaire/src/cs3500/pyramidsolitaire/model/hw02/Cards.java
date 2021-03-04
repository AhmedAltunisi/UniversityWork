package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a standard playing card.
 */

public class Cards {
  private final int value;
  private final String suit;

  /**
   * Constructs a playing card based on a given value and suite.
   *
   * @param value Represents the value of the card
   * @param suit  Represents the suit of the card
   * @throws IllegalArgumentException When given negative values or on that exceeds 13
   *                                  In addition, when given an invalid or null suit.
   */

  public Cards(int value, String suit) {

    if (suit == null || value > 13 || value < 1) {
      throw new IllegalArgumentException("Invalid Paramaters");
    }

    if ((!suit.equalsIgnoreCase("Hearts"))
        && !(suit.equalsIgnoreCase("Clubs"))
        && !(suit.equalsIgnoreCase("Diamonds"))
        && !(suit.equalsIgnoreCase("Spades"))) {
      throw new IllegalArgumentException("Invalid Suit");
    }
    this.value = value;
    this.suit = suit;
  }

  @Override
  public String toString() {
    switch (this.suit) {
      case "Diamonds" :
        return this.valueToString() + "♦";
      case "Spades" :
        return this.valueToString() + "♠";
      case "Hearts" :
        return this.valueToString() + "♥";
      default :
        return this.valueToString() + "♣";
    }
  }

  private String valueToString() {
    switch (this.value) {
      case 13 :
        return "K";
      case 12 :
        return "Q";
      case 11 :
        return "J";
      case 1 :
        return "A";
      default :
        return Integer.toString(this.value);
    }
  }


  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Cards)) {
      return false;
    }

    Cards that = (Cards) obj;

    return this.value == that.value && this.suit.equalsIgnoreCase(that.suit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value,suit);
  }

  public boolean validRemove(Cards that) {
    return this.value + that.value == 13;
  }

  public boolean validRemove() {
    return this.value == 13;
  }

  public int pointAdder(int points) {
    return this.value + points;
  }

}
