package model.shapes;

import java.util.Objects;

/**
 * Class that represents a color using RGB.
 */
public class Colors {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructor to create a color using rgb values.
   *
   * @param red   Red value of the color
   * @param green Green value of the color
   * @param blue  Blue value of the color
   */
  public Colors(int red, int green, int blue) {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("RGB cant be negative");
    }
    if (red > 255 || green > 255 || blue > 255) {
      throw new IllegalArgumentException("RGB can't exceed 255");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Constructor to create a color with a Colors input.
   *
   * @param color Color to use to create color
   */
  public Colors(Colors color) {
    this.red = color.red;
    this.green = color.green;
    this.blue = color.blue;
  }

  /**
   * Method to get the red value of this color.
   *
   * @return Red value of this color
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Method to get the green value of this color.
   *
   * @return Green value of this color
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Method to get the blue value of this color.
   *
   * @return Blue value of this color
   */
  public int getBlue() {
    return this.blue;
  }


  @Override
  public String toString() {
    return String.format("%d %d %d", this.red, this.green, this.blue);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Colors)) {
      return false;
    }

    Colors that = (Colors) obj;

    return this.red == that.red && this.green == that.green && that.blue == this.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }
}
