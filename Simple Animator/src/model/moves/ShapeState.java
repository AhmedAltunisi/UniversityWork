package model.moves;

import java.util.Objects;
import model.shapes.Colors;
import model.shapes.Position2D;

/**
 * Represents the state of a shape at a tick.
 */
public class ShapeState {
  private final int tick;
  private final Position2D pos;
  private final int height;
  private final int width;
  private final Colors color;

  /**
   * A constructor, it allows for the creation of shapestates.
   * This uses position2Ds and Colors (not the java one).
   * @param tick the tick
   * @param pos the position
   * @param height the height
   * @param width the width
   * @param color the color
   */
  public ShapeState(int tick, Position2D pos, int height, int width, Colors color) {
    if (pos == null) {
      throw new IllegalArgumentException("Position cannot be null");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }

    if (tick < 0 || height < 0 || width < 0) {
      throw new IllegalArgumentException("Illegal Values");
    }

    this.tick = tick;
    this.pos = pos;
    this.height = height;
    this.width = width;
    this.color = color;
  }

  /**
   * A constructor that takes in all the individual values of the shapestate.
   * @param tick tick
   * @param x xpos
   * @param y ypos
   * @param height height
   * @param width width
   * @param r red
   * @param g green
   * @param b blue
   */
  public ShapeState(int tick, int x, int y, int height, int width, int r, int g, int b) {

    if (tick < 0 || height < 0 || width < 0) {
      throw new IllegalArgumentException("Illegal Values");
    }
    this.tick = tick;
    this.pos = new Position2D(x ,y);
    this.height = height;
    this.width = width;
    this.color = new Colors(r, g, b);

  }

  /**
   * Used to create a new reference of a shapestate.
   * @param state  the shapestate.
   */
  public ShapeState(ShapeState state) {
    this.tick = state.getTick();
    this.pos = state.getPos();
    this.height = state.getHeight();
    this.width = state.getWidth();
    this.color = state.getColor();
  }

  /**
   * Returns the tick of the shape at this state.
   * @return the tick of the shape at this state.
   */
  public int getTick() {
    return this.tick;
  }

  /**
   * The position of the shape at this state.
   * @return The position of the shape at this state.
   */
  public Position2D getPos() {
    return new Position2D(this.pos);
  }

  /**
   * The width of the shape at this state.
   * @return The width of the shape at this state.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * The height of the shape at this state.
   * @return The height of the shape at this state.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * The color of the shape at this state.
   * @return The color of the shape at this state.
   */
  public Colors getColor() {
    return new Colors(this.color);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof ShapeState)) {
      return false;
    }

    ShapeState that = (ShapeState) other;

    return this.tick == that.tick
        && this.color.equals(that.color)
        && this.height == that.height
        && this.pos.equals(that.pos)
        && this.width == that.width;
  }

  @Override
  public int hashCode() {
    return Objects.hash(tick, pos, height, width, color);
  }

  @Override
  public String toString() {
    return String.format("%d %d %d %d %d %d %d",
    this.pos.getX(), this.pos.getY(), this.height, this.width, this.color.getRed(),
        this.color.getGreen(),
        this.color.getBlue());
  }
}
