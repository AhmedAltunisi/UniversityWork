package model;

import java.util.List;
import java.util.Map;
import model.moves.Moves;
import model.shapes.Shapes;

/**
 * Represents an interface for Easy Animator Models. They dictate the rules of the model.
 */
public interface EasyAnimatorModel {

  /**
   * Adds a move to the animation.
   * @param move The move to be added.
   */
  void addMoves(Moves move, Shapes shape);


  /**
   * Adds a shape to the animation.
   * @param shape The shape to be added.
   */
  void addShapes(Shapes shape);

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
   * Sets the canvas to the measurements requested.
   * @param height the height
   * @param width the width
   * @param x the leftmost x value
   * @param y the topmost y value
   */
  void setCanvas(int height, int width, int x, int y);

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

  /**
   * Removes a shape from the animation.
   * @param shape the shape to be removed
   */
  void removeShape(Shapes shape);

  /**
   * Removes a move from the animation.
   * @param move the move to be removed.
   * @param shape the shape associated with the move.
   */
  void removeMove(Moves move, Shapes shape);

  Shapes getShape(String name);
}
