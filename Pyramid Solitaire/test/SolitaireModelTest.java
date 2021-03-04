import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Cards;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.MultiPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

/** Tests for methods of {@link BasicPyramidSolitaire}s.
 */

public class SolitaireModelTest {

  @Test
  public void testDeck() {
    assertEquals(new Cards(1, "Diamonds"), new BasicPyramidSolitaire().getDeck().get(0));
    assertEquals(new Cards(2, "Diamonds"), new BasicPyramidSolitaire().getDeck().get(1));
    assertEquals(new Cards(13, "Hearts"), new BasicPyramidSolitaire().getDeck().get(51));
  }

  @Test
  public void testShuffle() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();
    List<Cards> deck = pyramid.getDeck();
    assertEquals(new Cards(1, "Diamonds"), deck.get(0));
    Collections.shuffle(deck);
    assertNotSame(new Cards(1, "Diamonds"), deck.get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGame() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();
    pyramid.startGame(null, false, 7,3);
    assertFalse(pyramid.isGameOver());
    pyramid.startGame(new ArrayList<Cards>(), false, 7,3);
    assertFalse(pyramid.isGameOver());
    pyramid.startGame(pyramid.getDeck(), false, 1000000,10000);
    assertFalse(pyramid.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemove() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();
    pyramid.startGame(pyramid.getDeck(), false, 7, 10);
    pyramid.remove(6,0,4,1);
  }

  @Test
  public void testRemoveCard() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();
    pyramid.startGame(pyramid.getDeck(), false, 7, 10);
    assertEquals(185, pyramid.getScore());
    pyramid.remove(6,2,6, 6);
    assertEquals(172, pyramid.getScore());
    pyramid.remove(6,5,6, 3);
    assertEquals(159, pyramid.getScore());
    pyramid.remove(6, 4);
    assertEquals(146, pyramid.getScore());
  }

  @Test
  public void testDrawCard() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();
    pyramid.startGame(pyramid.getDeck(), false, 7, 24);
    assertEquals(24, pyramid.getDrawCards().size());
    pyramid.discardDraw(1);
    assertEquals(23, pyramid.getDrawCards().size());
    assertEquals(23, pyramid.getNumDraw());
    pyramid.startGame(pyramid.getDeck(), false, 9, 3);
    pyramid.discardDraw(1);
    pyramid.discardDraw(1);
    pyramid.discardDraw(1);
    pyramid.discardDraw(1);
    pyramid.discardDraw(1);
    assertEquals(2, pyramid.getDrawCards().size());
    assertEquals(2, pyramid.getNumDraw());
  }

  @Test(expected = IllegalStateException.class)
  public void testIllegalState() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();
    assertFalse(pyramid.isGameOver());
    assertEquals(new Cards(10, "Diamonds"), pyramid.getCardAt(9,9));
    assertEquals(pyramid.getDeck(), pyramid.getDrawCards());
    assertEquals(-1, pyramid.getScore());
    pyramid.remove(3,3,3,3);
    pyramid.removeUsingDraw(10,5, 2);
  }

  @Test
  public void testRowsAndDrawDecks() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();

    assertEquals(-1, pyramid.getNumDraw());
    assertEquals(-1, pyramid.getNumRows());
    pyramid.startGame(pyramid.getDeck(), true, 7, 3);
    assertEquals(3, pyramid.getNumDraw());
    assertEquals(7, pyramid.getNumRows());
  }

  @Test
  public void gameOverByLackOfCards() {
    PyramidSolitaireModel<Cards> pyramid = new RelaxedPyramidSolitaire();
    PyramidSolitaireView view = new PyramidSolitaireTextualView(pyramid);
    pyramid.startGame(pyramid.getDeck(), false, 7, 3);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    assertEquals(true, pyramid.getDrawCards().isEmpty());
    pyramid.remove(6, 4);
    pyramid.remove(6, 6, 6, 2);
    pyramid.remove(6, 3, 6, 5);
    pyramid.remove(5, 2, 5, 5);
    pyramid.remove(5, 3, 5, 4);
    System.out.println(view.toString());
    pyramid.remove(4, 2);
    System.out.println(view.toString());
    assertEquals(true, pyramid.isGameOver());
  }


  //Multi Pyramid Tests

  @Test(expected = IllegalArgumentException.class)
  public void testStartMultiPyramidGame() {
    PyramidSolitaireModel<Cards> pyramid = new MultiPyramidSolitaire();
    pyramid.startGame(null, false, 7,3);
    assertFalse(pyramid.isGameOver());
    pyramid.startGame(new ArrayList<Cards>(), false, 7,3);
    assertFalse(pyramid.isGameOver());
    pyramid.startGame(pyramid.getDeck(), false, 1000000,10000);
    assertFalse(pyramid.isGameOver());
    pyramid.startGame(pyramid.getDeck(), false, 9,10);
    assertFalse(pyramid.isGameOver());
    pyramid.startGame(pyramid.getDeck(), false, 8,10);
    assertFalse(pyramid.isGameOver());
  }

  @Test
  public void testMultiRemoveCard() {
    PyramidSolitaireModel<Cards> pyramid = new MultiPyramidSolitaire();
    pyramid.startGame(pyramid.getDeck(), false, 7, 10);
    assertEquals(442, pyramid.getScore());
    pyramid.remove(6,1,6, 12);
    assertEquals(429, pyramid.getScore());
    pyramid.remove(6,5,6, 8);
    assertEquals(416, pyramid.getScore());
    pyramid.remove(6, 0);
    assertEquals(403, pyramid.getScore());
  }

  @Test
  public void testGetRowWidth() {
    PyramidSolitaireModel<Cards> pyramid = new MultiPyramidSolitaire();

    pyramid.startGame(pyramid.getDeck(), false, 1, 3);
    assertEquals(1, pyramid.getRowWidth(0));
    pyramid.startGame(pyramid.getDeck(), false, 2, 3);
    assertEquals(3, pyramid.getRowWidth(0));
    pyramid.startGame(pyramid.getDeck(), false, 4, 3);
    assertEquals(5, pyramid.getRowWidth(0));
    pyramid.startGame(pyramid.getDeck(), false, 6, 3);
    assertEquals(7, pyramid.getRowWidth(0));
    pyramid.startGame(pyramid.getDeck(), false, 8, 3);
    assertEquals(9, pyramid.getRowWidth(0));
  }


  //Relaxed Pyramid tests

  @Test(expected = IllegalArgumentException.class)
  public void testStartRelaxedGame() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();
    pyramid.startGame(null, false, 7,3);
    assertFalse(pyramid.isGameOver());
    pyramid.startGame(new ArrayList<Cards>(), false, 7,3);
    assertFalse(pyramid.isGameOver());
    pyramid.startGame(pyramid.getDeck(), false, 1000000,10000);
    assertFalse(pyramid.isGameOver());
  }

  @Test
  public void testRelaxedRemoveCard() {
    PyramidSolitaireModel<Cards> pyramid = new BasicPyramidSolitaire();
    pyramid.startGame(pyramid.getDeck(), false, 7, 10);
    assertEquals(185, pyramid.getScore());
    pyramid.removeUsingDraw(2, 6, 0);
    assertEquals(176, pyramid.getScore());
    pyramid.remove(6,1,5, 0);
    assertEquals(163, pyramid.getScore());
    pyramid.remove(6, 4);
    assertEquals(150, pyramid.getScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalRelaxedRemoveUsingDrawCard() {
    PyramidSolitaireModel<Cards> pyramid = new RelaxedPyramidSolitaire();
    pyramid.startGame(pyramid.getDeck(), false, 7, 10);
    pyramid.remove(6, 4);
    pyramid.discardDraw(3);
    pyramid.removeUsingDraw(2, 5, 4);
    pyramid.removeUsingDraw(1, 6 , 1);
    pyramid.remove(6,0,5,1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalRelaxedRemoveUsingOtherSemiExposedCard() {
    PyramidSolitaireModel<Cards> pyramid = new RelaxedPyramidSolitaire();
    pyramid.startGame(pyramid.getDeck(), false, 7, 10);
    pyramid.removeUsingDraw(1, 6 , 1);
    pyramid.remove(6,0,5,1);
  }

  @Test
  public void relaxedGameOverByLackOfCards() {
    PyramidSolitaireModel<Cards> pyramid = new RelaxedPyramidSolitaire();
    PyramidSolitaireView view = new PyramidSolitaireTextualView(pyramid);
    pyramid.startGame(pyramid.getDeck(), false, 7, 3);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    pyramid.discardDraw(0);
    assertEquals(true, pyramid.getDrawCards().isEmpty());
    pyramid.remove(6, 4);
    pyramid.remove(6, 6, 6, 2);
    pyramid.remove(6, 3, 6, 5);
    pyramid.remove(5, 2, 5, 5);
    pyramid.remove(5, 3, 5, 4);
    System.out.println(view.toString());
    pyramid.remove(4, 2);
    System.out.println(view.toString());
    assertEquals(true, pyramid.isGameOver());
  }



}
