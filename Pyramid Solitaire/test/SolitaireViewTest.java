import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Cards;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * Represents tests for the Solitaire view interface.
 */
public class SolitaireViewTest {
  @Test
  public void testView() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(pyramid);
    pyramid.startGame(pyramid.getDeck(), false, 4, 1);
    assertEquals("      A♦\n"
        + "    2♦  3♦\n"
        + "  4♦  5♦  6♦\n"
        + "7♦  8♦  9♦  10♦\n"
        + "Draw: J♦", view.toString());
  }

  @Test
  public void testMultiPyramidView() {
    PyramidSolitaireModel<Cards> pyramid = new MultiPyramidSolitaire();
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(pyramid);
    pyramid.startGame(pyramid.getDeck(), false, 5, 3);
    assertFalse(pyramid.isGameOver());
    System.out.println(view.toString());
    assertEquals("        A♦  .   2♦  .   3♦\n"
        + "      4♦  5♦  6♦  7♦  8♦  9♦\n"
        + "    10♦ J♦  Q♦  K♦  A♠  2♠  3♠\n"
        + "  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠\n"
        + "Q♠  K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣\n"
        + "Draw: 8♣, 9♣, 10♣", view.toString());


  }

  @Test
  public void oneRowMultiPyramidView() {
    PyramidSolitaireModel<Cards> pyramid = new MultiPyramidSolitaire();
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(pyramid);
    pyramid.startGame(pyramid.getDeck(), false, 1, 3);
    assertEquals("A♦\n"
        + "Draw: 2♦, 3♦, 4♦", view.toString());
  }

  @Test
  public void relaxedPyramidModelView() {
    PyramidSolitaireModel<Cards> pyramid = new RelaxedPyramidSolitaire();
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(pyramid);
    pyramid.startGame(pyramid.getDeck(), false, 7, 3);
    assertEquals("            A♦\n"
        + "          2♦  3♦\n"
        + "        4♦  5♦  6♦\n"
        + "      7♦  8♦  9♦  10♦\n"
        + "    J♦  Q♦  K♦  A♠  2♠\n"
        + "  3♠  4♠  5♠  6♠  7♠  8♠\n"
        + "9♠  10♠ J♠  Q♠  K♠  A♣  2♣\n"
        + "Draw: 3♣, 4♣, 5♣", view.toString());
  }
}
