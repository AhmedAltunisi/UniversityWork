package model.shapes;

import java.util.Objects;
import model.moves.ShapeState;
import view.ViewEllipse;
import view.ViewShapes;

/**
 * Represents an Ellipse, while similar to a circle, this shape's height and width can be different,
 * allowing for more interesting round objects.
 */
public class Ellipse extends AbstractShape {

  public Ellipse(String name) throws IllegalArgumentException {
    super(name);
  }

  @Override
  public ViewShapes makeViewShape(ShapeState state) {
    return new ViewEllipse(state.getPos(),
        state.getHeight(), state.getWidth(), state.getColor(), this.name);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Ellipse)) {
      return false;
    }
    Ellipse that = (Ellipse) other;
    return this.name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "shape " + this.name + " ellipse";
  }

}
