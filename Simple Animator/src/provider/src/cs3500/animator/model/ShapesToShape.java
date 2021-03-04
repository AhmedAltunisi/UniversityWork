package provider.src.cs3500.animator.model;

import java.awt.Color;
import model.shapes.Shapes;

/**
 * Class that is used to convert a shapes(our implementation) to a shape(provider's shape
 * implementation).
 */
public class ShapesToShape implements Shape {

  private final BasicShape shape;

  /**
   * Constructor that converts the shapes to a shape.
   * @param shapes Shapes to convert
   */
  public ShapesToShape(Shapes shapes) {
    String s = shapes.toString();
    String name = shapes.getName();

    this.shape = new BasicShape(name, s.substring(7 + name.length()));
  }

  /**
   * Gets the scale of the Shape.
   *
   * @return a Position2D representing the width (x) and height (y) of this Shape
   */
  @Override
  public Position2D getScale() {
    return this.shape.getScale();
  }

  /**
   * Gets the position of the Shape.
   *
   * @return a Position2D representing the x and y coords of this Shape
   */
  @Override
  public Position2D getPosition() {
    return this.shape.getPosition();
  }

  /**
   * Gets the color of the Shape.
   *
   * @return a Color object representing the color of this Shape
   */
  @Override
  public Color getColor() {
    return this.shape.getColor();
  }

  /**
   * Returns the name of the Shape.
   *
   * @return The String name of the Shape
   */
  @Override
  public String getName() {
    return this.shape.getName();
  }

  /**
   * Returns the type of the Shape.
   *
   * @return the String type of the Shape
   */
  @Override
  public String getType() {
    return this.shape.getType();
  }
}
