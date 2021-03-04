package provider.src.cs3500.animator.model;

import java.util.Objects;

/** This class represents a 2D position. */
public final class Position2D {
  private int x;
  private int y;

  /**
   * Initialize this object to the specified position.
   *
   * @param x The x value of the position
   * @param y The y value of the position
   */
  public Position2D(int x, int y) {
    this.setX(x);
    this.setY(y);
  }

  /**
   * Copy constructor.
   *
   * @param v Position to be copied
   */
  public Position2D(Position2D v) {
    this.setX(v.x);
    this.setY(v.y);
  }

  /**
   * Get the x coordinate of this position.
   *
   * @return Returns the x value of the position
   */
  public int getX() {
    return x;
  }

  /**
   * Set the x coordinate of this object.
   *
   * @param x The desired x value of the position
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Get the y coordinate of this position.
   *
   * @return The y value of the position
   */
  public int getY() {
    return y;
  }

  /**
   * Set the y coordiante of this object.
   *
   * @param y The desired y value of the position
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * This is to see if any of the two Position2D points are the same.
   *
   * @param a The Position to be checked
   * @return Whether the positions are the same or not
   */
  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Position2D)) {
      return false;
    }

    Position2D that = (Position2D) a;

    return (this.x == that.x) && (this.y == that.y);
  }

  /**
   * This returns the hash value of the position.
   *
   * @return The integer hash value of the position
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
