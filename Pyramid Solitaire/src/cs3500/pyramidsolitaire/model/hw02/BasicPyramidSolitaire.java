package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.Set;

/**
 * Represents a basic version of pyramid solitaire.
 */

public class BasicPyramidSolitaire implements PyramidSolitaireModel<Cards> {

  protected boolean gameOn;
  protected List<Cards> cards;
  protected int rows;
  protected int draw;
  protected List<Cards> drawCards;
  protected final List<Cards> removedCards;

  /**
   * Constructs a basic version of pyramid solitaire that can be accessed by anyone to start a game.
   */

  public BasicPyramidSolitaire() {

    this.gameOn = false;
    this.cards = new ArrayList<>();
    this.rows = 0;
    this.draw = 0;
    this.drawCards = new ArrayList<>();
    this.removedCards = new ArrayList<>();
  }

  /**
   * Constructs a basic version of pyramid solitaire for testing purposes.
   *
   * @param cards   a deck of cards
   */

  public BasicPyramidSolitaire(boolean gameOn, List<Cards> cards, int rows, int draw) {
    this.gameOn = gameOn;
    this.cards = cards;
    this.rows = rows;
    this.draw = draw;
    this.drawCards = new ArrayList<>();
    this.removedCards = new ArrayList<>();
  }


  /**
   * Return a valid and complete deck of cards for a game of Pyramid Solitaire. There is no
   * restriction imposed on the ordering of these cards in the deck. The validity of the deck is
   * determined by the rules of the specific game in the classes implementing this interface.
   *
   * @return the deck of cards as a list
   */
  @Override
  public List<Cards> getDeck() {
    List<Integer> values =
        new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13));
    List<String> suit =
        new ArrayList<>(Arrays.asList("Diamonds", "Spades", "Clubs", "Hearts"));
    List<Cards> card = new ArrayList<>();

    for (String s : suit) {
      for (Integer value : values) {
        card.add(new Cards(value, s));
      }
    }
    return card;
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
    if (deck == null || deck.size() != 52) {
      throw new IllegalArgumentException("Invalid deck");
    }

    if (((numRows * (numRows + 1)) / 2) + numDraw > 52
        || numRows < 1 || numDraw < 0 || this.duplicateCards(deck)) {
      throw new IllegalArgumentException("Invalid deck setup");
    }
    this.gameOn = true;
    this.rows = numRows;
    this.draw = numDraw;
    this.cards = new ArrayList<>(deck);

    if (shuffle) {
      Collections.shuffle(this.cards);
    }

    this.removeToFitRows();

    if (this.cards.size() + this.drawCards.size() != 52) {
      throw new IllegalArgumentException("invalid game setup");
    }
  }

  /**
   * Removes cards from the deck as to fit with the number of rows requested by the game.
   */
  private void removeToFitRows() {

    List<Cards> fixedDeck = new ArrayList<>();
    int rows = this.rows - 1;

    for (int i = 0; i < this.getTotalCards(rows); i++) {
      fixedDeck.add(this.cards.get(i));
    }
    this.cards.removeAll(fixedDeck);
    this.drawCards = this.cards;
    this.cards = fixedDeck;
  }

  private boolean duplicateCards(List<Cards> deck) {
    if (deck == null) {
      throw new IllegalArgumentException("Invalid deck");
    }
    Set<Cards> hashSet = new HashSet<>(deck);
    return deck.size() != hashSet.size();
  }

  /**
   * Execute a two-card move on the pyramid, using the two specified card positions.
   *
   * @param row1  row of first card position, numbered from 0 from the top of the pyramid
   * @param card1 card of first card position, numbered from 0 from left
   * @param row2  row of second card position
   * @param card2 card of second card position
   * @throws IllegalArgumentException if the move is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {
    if (!(this.gameOn)) {
      throw new IllegalStateException("The game hasn't started");
    }
    if (row1 > this.rows - 1 || row2 > this.rows - 1
        || card1 > this.getRowWidth(row1) - 1 || card2 > this.getRowWidth(row2) - 1
        || card1 <= -1 || card2 <= -1) {
      throw new IllegalArgumentException("Invalid remove");
    }
    else {
      Cards cardFirst = this.getCardAt(row1, card1);
      Cards cardSecond = this.getCardAt(row2, card2);

      if (cardFirst == null || cardSecond == null) {
        throw new IllegalArgumentException("One of the cards is already removed");
      }
      else {

        if (cardFirst.validRemove(cardSecond)
            && this.isCardExposed(row1, card1, row2, card2)) {

          this.removedCards.add(this.getCardAt(row1, card1));
          this.removedCards.add(this.getCardAt(row2, card2));
          this.cards.set(this.getCardLocation(row1, card1), null);
          this.cards.set(this.getCardLocation(row2, card2), null);
        }
        else {
          throw new IllegalArgumentException("Invalid Remove");
        }
      }
    }
  }

  /**
   * Execute a single-card move on the pyramid, using the specified card position.
   *
   * @param row  row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the move is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    if (!(this.gameOn)) {
      throw new IllegalStateException("The game hasn't started");
    }
    if (row > this.rows - 1 || card > this.getRowWidth(row) - 1 || card <= -1) {
      throw new IllegalArgumentException("Invalid remove");
    }
    Cards removedCard = this.getCardAt(row, card);
    if (removedCard != null && removedCard.validRemove() && this.isCardExposed(row, card)) {
      this.removedCards.add(this.getCardAt(row, card));
      this.cards.set(this.getCardLocation(row, card), null);
    }
    else {
      throw new IllegalArgumentException("Invalid remove");
    }

  }

  /**
   * Execute a two-card move, using the specified card from the draw pile and the specified card
   * from the pyramid.
   *
   * @param drawIndex the index of the card within the draw pile.
   * @param row       row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card      card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the move is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (!(this.gameOn)) {
      throw new IllegalStateException("The Game Hasn't Started");
    }
    if (row > this.rows || card > this.getRowWidth(row) - 1 || card < 0) {
      throw new IllegalArgumentException("invalid card position");
    }

    if (drawIndex > this.drawCards.size() - 1 || drawIndex < 0) {
      throw new IllegalArgumentException("Invalid draw index");
    }
    Cards removedCard = this.getCardAt(row,card);
    if (removedCard != null
        && removedCard.validRemove(this.drawCards.get(drawIndex)) && this.isCardExposed(row,card)) {
      this.removedCards.add(this.getCardAt(row, card));
      this.discardDraw(drawIndex);
      this.cards.set(this.getCardLocation(row, card), null);
    }
    else {
      throw new IllegalArgumentException("Invalid remove");
    }
  }

  protected boolean isCardExposed(int row, int card) {
    if (this.getCardAt(row, card) == null) {
      return false;
    }
    else {
      if (row == this.rows - 1 && this.getCardAt(row, card) != null) {
        return true;
      } else {
        if (row == this.rows - 1) {
          return false;
        } else {
          if (this.getCardAt(row, card) != null) {
            return this.getCardAt(row + 1, card) == null
                && this.getCardAt(row + 1, card + 1) == null;
          } else {
            return false;
          }
        }
      }
    }

  }

  protected boolean isCardExposed(int row1, int card1, int row2, int card2) {
    return this.isCardExposed(row1, card1) && this.isCardExposed(row2, card2);
  }

  /**
   * Discards an individual card from the draw pile.
   *
   * @param drawIndex the card to be discarded
   * @throws IllegalArgumentException if the index is invalid or no card is present there.
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {
    if (!(this.gameOn)) {
      throw new IllegalStateException("The game hasn't started");
    }

    if (this.drawCards.size() - 1 < drawIndex || drawIndex < 0) {
      throw new IllegalArgumentException("Index not found");
    }

    if (drawIndex <= this.draw - 1) {
      this.drawCards.remove(drawIndex);
    }
    else {
      throw new IllegalArgumentException("Invalid index");
    }
  }

  /**
   * Returns the number of rows originally in the pyramid, or -1 if the game hasn't been started.
   *
   * @return the height of the pyramid, or -1
   */
  @Override
  public int getNumRows() {
    if (!(this.gameOn)) {
      return -1;
    }
    else {
      return this.rows;
    }
  }

  /**
   * Returns the maximum number of visible cards in the draw pile, or -1 if the game hasn't been
   * started.
   *
   * @return the number of visible cards in the draw pile, or -1
   */
  @Override
  public int getNumDraw() {
    if (!(this.gameOn)) {
      return -1;
    } else {
      if (this.drawCards.size() < this.draw) {
        return this.drawCards.size();
      }
      else {
        return this.draw;
      }
    }
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
    return row + 1;
  }
  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise
   * @throws IllegalStateException if the game hasn't been started yet
   */

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (!(this.gameOn)) {
      throw new IllegalStateException("The game hasn't started");
    }

    if (this.cards.size() == this.removedCards.size()) {

      return true;
    }

    if (this.drawCards.isEmpty()) {
      return !(this.areThereCardsToRemove());
    }
    return false;
  }

  /**
   * Checks whether there are any exposed cards left to remove.
   * @return a boolean that tells the game whether there are cards to remove
   */
  private boolean areThereCardsToRemove() {
    boolean maybe = false;
    List<Cards> exposedCards = this.allExposedCards();

    for (int i = 0; i < exposedCards.size(); i++) {
      Cards exposedCard = exposedCards.get(i);
      for (Cards card : exposedCards) {
        if (exposedCard.validRemove() || card.validRemove(exposedCard) || card.validRemove()) {
          maybe = true;
        }
      }
    }
    return maybe;
  }

  protected List<Cards> allExposedCards() {
    List<Cards> exposedCards = new ArrayList<>();
    for (int i = 0; i < this.rows; i++) {
      for (int y = 0; y < this.getRowWidth(i); y++) {
        if ( this.isCardExposed(i, y)) {
          exposedCards.add(this.getCardAt(i,y));
        }
      }
    }
    return exposedCards;
  }

  /**
   * Return the current score, which is the sum of the values of the cards remaining in the
   * pyramid.
   *
   * @return the score
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public int getScore() throws IllegalStateException {
    if (!(this.gameOn)) {
      throw new IllegalStateException("The game hasn't started");
    }

    int points = 0;

    for (Cards card : this.cards) {
      if (card != null) {
        points = card.pointAdder(points);
      }
    }
    return points;
  }

  /**
   * Returns the card at the specified coordinates.
   *
   * @param row  row of the desired card (0-indexed from the top)
   * @param card column of the desired card (0-indexed from the left)
   * @return the card at the given position, or <code>null</code> if no card is there
   * @throws IllegalArgumentException if the coordinates are invalid
   * @throws IllegalStateException    if the game hasn't been started yet
   */
  @Override
  public Cards getCardAt(int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    if (!(this.gameOn)) {
      throw new IllegalStateException("Game hasn't started");
    }
    if (card <= -1 || card > this.getRowWidth(row) ) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    return this.cards.get(this.getCardLocation(row, card));
  }

  /**
   * Returns the index of a card in a list, given the card position within the pyramid.
   * @param row   row of the desired card (0-indexed from the top)
   * @param card  column of the desired card (0-indexed from the left)
   * @return the index of a card within the list of cards
   */
  protected int getCardLocation(int row, int card) {
    if (row == 0) {
      return card;
    }
    else {
      if (row == this.rows - 1) {
        return this.cards.size() - this.getRowWidth(row) + card;
      }
      else {
        int counter = 0;
        int location = 0;
        for (int i = 0; i < this.rows; i++) {
          for (int y = 0; y < this.getRowWidth(i); y++) {
            if (i == row && y == card) {
              return location = counter;
            } else {
              counter += 1;
            }
          }
        }
        return location;
      }
    }
  }

  /**
   * Returns the currently available draw cards. There should be at most {@link
   * PyramidSolitaireModel#getNumDraw} cards (the number specified when the game started) -- there
   * may be fewer, if cards have been removed.
   *
   * @return the ordered list of available draw cards
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public List<Cards> getDrawCards() throws IllegalStateException {

    if (!(this.gameOn)) {
      throw new IllegalStateException("Game should start before asking");
    }
    List<Cards> availableDrawCards = new ArrayList<>();

    if (this.drawCards.size() < this.draw) {
      availableDrawCards = this.drawCards;
    }
    else {
      for (int i = 0; i < this.draw; i++) {
        availableDrawCards.add(this.drawCards.get(i));
      }
    }

    return availableDrawCards;
  }

  /**
   * Gets the total amount of cards in the pyramid from a requested row.
   * @param row   The row requested
   * @return the amount of cards from the top to that row
   */
  private int getTotalCards(int row) throws IllegalArgumentException, IllegalStateException {
    return (((row + 1) * ((row + 2))) / 2);
  }
}
