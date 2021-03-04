package provider.src.cs3500.animator.model;

import java.awt.Color;

/**
 * Represents a shape object in the animator.
 *
 * <p>A Shape shall keep track of a name-tag that it will use for access and comparison, a type with
 * which to indicate how to draw the Shape in a visual animation, the scale of its bounding boxes,
 * and its RGB color fill values. A Shape also keeps track of its own position within an Animation.
 */
public interface Shape {

  /**
   * Gets the scale of the Shape.
   *
   * @return a Position2D representing the width (x) and height (y) of this Shape
   */
  Position2D getScale();

  /**
   * Gets the position of the Shape.
   *
   * @return a Position2D representing the x and y coords of this Shape
   */
  Position2D getPosition();

  /**
   * Gets the color of the Shape.
   *
   * @return a Color object representing the color of this Shape
   */
  Color getColor();

  /**
   * Returns the name of the Shape.
   *
   * @return The String name of the Shape
   */
  String getName();

  /**
   * Returns the type of the Shape.
   *
   * @return the String type of the Shape
   */
  String getType();
}
