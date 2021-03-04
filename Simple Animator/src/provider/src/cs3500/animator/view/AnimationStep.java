package provider.src.cs3500.animator.view;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Helper class for visual views that stores interpolated shape state information conveniently and
 * implements a "draw" function for use in panel rendering.
 */
public class AnimationStep {
  public final int xVal;
  public final int yVal;
  public final int height;
  public final int width;
  public final Color color;
  public final String type;
  public final String name;

  /**
   * Public constructor of the animation step object.
   *
   * @param x The x value of the shapes position
   * @param y The y value of the shapes position
   * @param h The height value of the shape
   * @param w The width value of the shape
   * @param color The rgb color of the shape
   * @param type The type of the shape
   * @param name The name of the shape
   */
  public AnimationStep(int x, int y, int h, int w, Color color, String type, String name) {
    this.xVal = x;
    this.yVal = y;
    this.height = h;
    this.width = w;
    this.color = color;
    this.type = type;
    this.name = name;
  }

  /**
   * Draw function that places this shape on a Graphics2D object according to this shape's type.
   *
   * <p>Current available types are rectangle and ellipse. If any types are added to the model, they
   * should be implemented here.
   *
   * @param g2d the Graphics2D object to draw on
   */
  public void draw(Graphics2D g2d) {
    if (this.type.equals("rectangle")) {
      g2d.setColor(color);
      g2d.fillRect(xVal, yVal, width, height);
    } else if (this.type.equals("ellipse")) {
      g2d.setColor(color);
      g2d.fillOval(xVal - width / 2, yVal - height / 2, width, height);
    }
  }
}
