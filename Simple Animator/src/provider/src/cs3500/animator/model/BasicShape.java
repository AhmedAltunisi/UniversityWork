package provider.src.cs3500.animator.model;

import java.awt.Color;
import java.util.Objects;

/**
 * Basic implementation of the shape interface for displaying a shape to animate.
 *
 * <p>All fields of this Shape are final, meaning that Operations performed on a Shape must be done
 * with getters and constructors. This pattern is robust and useful for constructing Animation
 * timelines.
 */
public class BasicShape implements Shape {
  private final Color color;
  private final Position2D scale;
  private final Position2D position;
  private final String name;
  private final String type;

  /**
   * Base constructor for the basic shape class.
   *
   * @param name A name tag for the shape * @param type The type of the shape
   * @param position an initial position for the Shape
   * @param color The color fill of the shape
   * @param scale The scale of the shape currently
   */
  public BasicShape(String name, String type, Position2D position, Position2D scale, Color color) {
    Objects.requireNonNull(position);
    Objects.requireNonNull(scale);
    Objects.requireNonNull(color);
    Objects.requireNonNull(name);
    Objects.requireNonNull(type);

    if (scale.getX() < 0 || scale.getY() < 0) {
      throw new IllegalArgumentException("Scale parameters must be non-negative");
    }

    this.position = position;
    this.scale = scale;
    this.color = color;
    this.name = name;
    this.type = type;
  }

  /**
   * Initializes a basic shape with default parameters.
   *
   * @param name The name of the shape
   * @param type The type of the shape
   */
  public BasicShape(String name, String type) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(type);

    this.position = new Position2D(0, 0);
    this.scale = new Position2D(0, 0);
    this.color = new Color(0, 0, 0);
    this.name = name;
    this.type = type;
  }

  /**
   * Copy constructor for the Basic Shape.
   *
   * @param s The shape to be copied
   */
  public BasicShape(Shape s) {
    this.position = s.getPosition();
    this.scale = s.getScale();
    this.color = s.getColor();
    this.name = s.getName();
    this.type = s.getType();
  }

  /**
   * Returns the current scale of the shape.
   *
   * @return The current scale of the shape
   */
  @Override
  public Position2D getScale() {
    return this.scale;
  }

  /**
   * Gets the current position of the Shape in the Animation.
   *
   * <p>The Shape does not verify that its own position is valid, because it does not know the
   * parameters of the surrounding Animation.
   *
   * @return the position of the shape as a Position2D
   */
  @Override
  public Position2D getPosition() {
    return new Position2D(this.position);
  }

  /**
   * Returns the current color fill of the shape.
   *
   * @return The current color of the shape
   */
  @Override
  public Color getColor() {
    return new Color(this.color.getRGB());
  }

  /**
   * Gets the name-tag associated with this Shape.
   *
   * @return the name of the Shape as a String
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Gets the type-tag associated with this Shape.
   *
   * @return the type of the Shape as a member of the ShapeType enum
   */
  @Override
  public String getType() {
    return this.type;
  }

  /**
   * Hashcode is based purely on name of the Shape, since we will never want two Shapes with the
   * same name in an Animation, but all other fields (except Shape type) can change as the Animation
   * runs (when the shape is reconstructed).
   *
   * @return the hashed name of the Shape
   */
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  /**
   * Equals is based purely on the name of the Shape.
   *
   * @param that an Object for comparison
   * @return whether this.name is equal to that.name (if that is a BasicShape)
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof BasicShape)) {
      return false;
    }

    return (this.name.equals(((BasicShape) that).name));
  }
}
