package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Cards;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * A factory class that facilitates the creation of Pyramid Solitaire models.
 * Currently, you can choose between Basic, Relaxed and Multi-Pyramid Pyramid Solitaire!
 */
public class PyramidSolitaireCreator {

  /**
   * The type of Game modes currently available. All are very fun.
   */
  public enum GameType {
    BASIC, RELAXED, MULTIPYRAMID;
  }

  /**
   * A factory class that facilitates the creation of Pyramid Solitaire Models.
   * @param type The game mode desired.
   * @return Returns the pyramid solitaire game mode of the user's choosing.
   */
  public static PyramidSolitaireModel<Cards> create(GameType type) {
    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case MULTIPYRAMID:
        return new MultiPyramidSolitaire();
      default:
        return null;
    }
  }

}
