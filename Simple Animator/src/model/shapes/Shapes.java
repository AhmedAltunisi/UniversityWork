package model.shapes;

import model.moves.ShapeState;
import view.ViewShapes;

/**
 * Class that represents a shape that has a height, width, position, and color.
 */
public interface Shapes {

  /**
   * Method that returns the name of the shape.
   *
   * @return Name of shape
   */
  String getName();

  /**
   * Creates a ViewShape that corresponds with the type of the shape.
   * @param state the state of the shape when instantiated.
   * @return A ViewShape.
   */
  ViewShapes makeViewShape(ShapeState state);

}
