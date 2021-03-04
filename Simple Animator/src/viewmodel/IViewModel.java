package viewmodel;

import java.util.List;
import java.util.Map;
import model.moves.Moves;
import model.shapes.Shapes;

/**
 * Represents an interface for viewModels. They allow the view to access a model that can only
 * provide information, not alter it.
 */
public interface IViewModel {

  /**
   * Obtains the hashmap of shapes and it's list of moves.
   *
   * @return the hashmap of shapes and it's list of moves.
   */
  Map<Shapes, List<Moves>> getShapesAndMoves();


  /**
   * Returns the last tick in the animation aka at what tick the animation will end on.
   * @return the last tick.
   */
  int getLastTick();

  /**
   * The height of the canvas.
   * @return the height of the canvas.
   */
  int getHeight();

  /**
   * The width of the canvas.
   * @return the width of the canvas.
   */
  int getWidth();

  /**
   * The leftmost x value of the canvas.
   * @return the leftmost x value of the canvas.
   */
  int getX();

  /**
   * The topmost y value of the canvas.
   * @return the topmost y value of the canvas.
   */
  int getY();


}
