package model.shapes;

import java.util.Objects;
import model.moves.ShapeState;
import view.ViewRect;
import view.ViewShapes;

/**
 * Represents a rectangle. The creme de la creme of shapes, it has 4 sides and it's width and height
 * can be as different as you would like it to be.
 */
public class Rectangle extends AbstractShape {


  /**
   * Constructor that allows creation of a shape utilizing rgb integer values for creation.
   *
   * @param name   Name of shape
   * @throws IllegalArgumentException If any of the values are inputted into the constructor are
   *                                  invalid
   */
  public Rectangle(String name) throws IllegalArgumentException {
    super(name);
  }

  @Override
  public ViewShapes makeViewShape(ShapeState state) {
    return new ViewRect(state.getPos(), state.getHeight(), state.getWidth(), state.getColor(),
        this.name);
  }


  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Rectangle)) {
      return false;
    }
    Rectangle that = (Rectangle) other;
    return this.name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "shape " + this.name + " rectangle";
  }

}
