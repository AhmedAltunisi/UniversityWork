package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Cards;
import java.util.ArrayList;
import java.util.List;

/**
 * A version of Pyramid soliatire with relaxed rules. Now if you have two overlapping cards that
 * need a remove, you can do it. However, make sure that the card on top is only covering one card.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  /**
   * This model extends the basic model with only a few rules changes.
   * As such, this does not need new fields to work with!
   */
  public RelaxedPyramidSolitaire() {
  }

  protected boolean isCardExposed(int row1, int card1, int row2, int card2) {
    if (super.getCardAt(row1, card1) == null || super.getCardAt(row2, card2) == null) {

      return false;
    }
    else {
      if (row1 > row2 && card1 == card2 && card1 == 0 && super.getCardAt(row1, 1) == null) {

        return super.isCardExposed(row1, card1);
      } else {
        if (row1 > row2 && card1 == card2 && card1 == super.getRowWidth(row1) - 1
            && super.getCardAt(row1, card1 - 1) == null) {
          return super.isCardExposed(row1, card1);
        } else {
          if (row2 > row1 && card1 == card2 && card2 == 0 && super.getCardAt(row2, 1) == null) {

            return super.isCardExposed(row2, card2);
          } else {
            if (row2 > row1 && card1 == card2 && card2 == super.getRowWidth(row2) - 1
                && super.getCardAt(row2, card2 + 1) == null) {

              return super.isCardExposed(row2, card2);
            } else {
              if (row1 > row2 && (card1 == card2 || card2 + 1 == card1)
                  && (super.getCardAt(row1, card2) == null
                  || super.getCardAt(row1, card2 + 1) == null)) {
                return super.isCardExposed(row1, card1);
              } else {
                if (row2 > row1 && (card1 == card2 || card1 + 1 == card2)
                    && (super.getCardAt(row2, card1) == null
                    || super.getCardAt(row2, card1 + 1) == null)) {
                  return super.isCardExposed(row2, card2);
                } else {

                  return super.isCardExposed(row1, card1) && super.isCardExposed(row2, card2);
                }
              }
            }
          }
        }
      }
    }

  }

  protected List<Cards> allExposedCards() {
    List<Cards> exposedCards = new ArrayList<>();
    for (int i = 0; i < this.rows; i++) {
      for (int y = 0; y < this.getRowWidth(i); y++) {
        if (i == this.rows - 1 && this.isCardExposed(i,y)) {
          exposedCards.add(this.getCardAt(i, y));
        } else {
          if (this.isCardExposed(i, y, i + 1, y)
              && this.getCardAt(i,y).validRemove(this.getCardAt(i + 1, y))) {
            exposedCards.add(this.getCardAt(i, y));
            exposedCards.add(this.getCardAt(i + 1, y));
          } else {
            if (this.isCardExposed(i, y, i + 1, y + 1)
                && this.getCardAt(i,y).validRemove(this.getCardAt(i + 1, y + 1))) {
              exposedCards.add(this.getCardAt(i, y));
              exposedCards.add(this.getCardAt(i + 1, y + 1));
            } else {
              if (this.isCardExposed(i, y)) {
                exposedCards.add(this.getCardAt(i, y));
              }
            }
          }
        }
      }
    }
    return exposedCards;

  }
}
