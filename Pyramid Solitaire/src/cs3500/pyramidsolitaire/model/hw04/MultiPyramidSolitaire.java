package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Cards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *A Pyramid Solitaire model that allows the player to play Solitaire with an extended deck of cards
 * and two extra pyramids.
 */
public class MultiPyramidSolitaire extends BasicPyramidSolitaire {

  private int deviationFromRowIndex;

  public MultiPyramidSolitaire() {
    this.deviationFromRowIndex = 0;
  }

  @Override
  public List<Cards> getDeck() {
    List<Cards> deck;
    deck = super.getDeck();
    deck.addAll(super.getDeck());
    return deck;
  }

  /**
   * <p>Deal a new game of Pyramid Solitaire.
   * The cards to be used and their order are specified by the the given deck, unless the {@code
   * shuffle} parameter indicates the order should be ignored.</p>
   *
   * <p>This method first verifies that the deck is valid. It deals cards in rows
   * (left-to-right, top-to-bottom) into the characteristic pyramid shape with the specified number
   * of rows, followed by the specified number of draw cards. When {@code shuffle} is {@code false},
   * the 0th card in {@code deck} is used as the first card dealt.</p>
   *
   * <p>This method should have no other side effects, and should work for any valid arguments.</p>
   *
   * @param deck    the deck to be dealt
   * @param shuffle if {@code false}, use the order as given by {@code deck}, otherwise use a
   *                randomly shuffled order
   * @param numRows number of rows in the pyramid
   * @param numDraw number of draw cards available at a time
   * @throws IllegalArgumentException if the deck is null or invalid, the number of pyramid rows or
   *                                  number of available draw cards is non-positive, or a full
   *                                  pyramid and draw pile cannot be dealt with the number of given
   *                                  cards in deck
   */
  @Override
  public void startGame(List<Cards> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    if (deck == null || deck.size() != 104 || this.duplicateCards(deck)) {
      throw new IllegalArgumentException("Invalid deck");
    }
    TrapezoidTemplate trapezoid = new TrapezoidTemplate(numRows);
    trapezoid.buildTemplateTrapezoid();

    if (numRows > 8 || numRows < 1 || numDraw < 0) {
      throw new IllegalArgumentException("Invalid deck setup");
    }
    this.gameOn = true;
    this.rows = numRows;
    this.draw = numDraw;
    this.cards = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(this.cards);
    }
    this.addNullToFirstRow();
    this.removeToDeck(trapezoid.trapezoidSize());
    this.cards = this.cards.subList(0, trapezoid.trapezoidSize());



    if (this.actualSize() + draw > 104) {
      throw new IllegalArgumentException("Invalid deck setup");
    }

  }

  private void addNullToFirstRow() {
    List<Cards> modifiedDeck = new ArrayList<>();
    if (this.rows == 1) {
      modifiedDeck = this.cards;

    } else {
      for (int i = 0; i <= 1; i++) {
        modifiedDeck.add(this.cards.get(i));
        this.addNulls(this.howManyNullsToAdd(), modifiedDeck);
      }
      this.deviationFromRowIndex = modifiedDeck.size() ;
      modifiedDeck.addAll(this.cards.subList(2, this.cards.size() - 1));
    }
    this.cards = modifiedDeck;

  }

  private void addNulls(int nulls, List<Cards> deck) {
    if (nulls > 0) {
      deck.add(null);
      this.removedCards.add(null);
      addNulls(nulls - 1, deck);
    }
  }

  private int howManyNullsToAdd() {
    if (this.rows <= 3) {
      return 0;
    } else {
      if (this.rows % 2 != 0) {
        return this.roadto2(this.rows - 1, 0);
      } else {
        return this.roadto2(this.rows, 0);
      }
    }
  }

  private int roadto2(int currentNum, int counter) {
    if (currentNum == 2) {
      return counter;
    } else {
      return roadto2(currentNum - 2, counter + 1);
    }
  }

  private boolean duplicateCards(List<Cards> deck) {
    List<Cards> validDeck = this.getDeck();
    boolean areThere = false;
    List<Cards> likeCards = new ArrayList<>();
    for (int i = 0; i < validDeck.size(); i++) {
      for (Cards value : deck) {
        if (validDeck.get(i).toString().equals(value.toString())) {
          likeCards.add(deck.get(i));
        }
      }
      if (likeCards.size() == 2) {
        likeCards = new ArrayList<>();
      }
      else {
        areThere = true;
      }
    }
    return areThere;
  }

  /**
   * Removes cards from the deck as to fit with the number of rows requested by the game.
   */
  private void removeToDeck(int sizeOfDeck) {
    for (int i = 0; i < this.rows - 1; i++) {
      for (int y = 0; y < this.getRowWidth(i); y++) {
        if (this.getCardAt(i,y) == null && this.getCardAt(i, y + 1) == null) {
          this.cards.add(this.getCardLocation(i + 1, y + 1), null);
          this.removedCards.add(null);
        }
      }
    }
    this.drawCards.addAll(this.cards.subList(sizeOfDeck, this.cards.size()));
  }

  private int actualSize() {
    List<Cards> deck = this.cards;
    int counter = 0;
    for (Cards value : deck) {
      if (value != null) {
        counter++;
      }
    }
    return counter;
  }


  /**
   * Returns the width of the requested row, measured from the leftmost card to the rightmost card
   * (inclusive) as the game is initially dealt.
   *
   * @param row the desired row (0-indexed)
   * @return the number of spaces needed to deal out that row
   * @throws IllegalArgumentException if the row is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public int getRowWidth(int row) throws IllegalArgumentException, IllegalStateException {
    if (!(this.gameOn)) {
      throw new IllegalStateException("Game hasn't started");
    }
    if (row < 0 || row > this.rows) {
      throw new IllegalArgumentException("Invalid row");
    }
    return row + 1 + this.deviationFromRowIndex;
  }
}