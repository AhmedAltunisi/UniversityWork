package view;

import java.awt.Color;
import java.awt.Graphics2D;
import model.shapes.Colors;
import model.shapes.Position2D;

/**
 * Represents an abstract ViewShape.
 */
public abstract class AbstractViewShape implements ViewShapes {
  protected int x;
  protected int y;
  protected int height;
  protected int width;
  protected Color color;
  String name;

  /**
   * A constructor that create a viewShape using more understandable fields.
   * @param x xpos
   * @param y ypos
   * @param height height
   * @param width width
   * @param color color
   * @param name the name of the shape
   */
  public AbstractViewShape(int x, int y, int height, int width, Color color, String name) {
    this.x = x;
    this.y = y;
    this.height = height;
    this.width = width;
    this.color = color;
    this.name = name;
  }

  /**
   * A constructor that creates a ViewShape using fields that are mostly used by the model.
   * @param pos 2Dposition
   * @param height height
   * @param width width
   * @param color color
   * @param name the name of the shape
   */
  public AbstractViewShape(Position2D pos, int height, int width, Colors color, String name) {
    this.x = pos.getX();
    this.y = pos.getY();
    this.height = height;
    this.width = width;
    this.color = new Color(color.getRed(), color.getGreen(), color.getBlue());
    this.name = name;
  }


  public abstract void render(Graphics2D g);

  public String getName() {
    return this.name;
  }
}
