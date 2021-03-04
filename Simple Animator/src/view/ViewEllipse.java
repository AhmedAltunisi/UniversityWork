package view;

import java.awt.Color;
import java.awt.Graphics2D;
import model.moves.ShapeState;
import model.shapes.Colors;
import model.shapes.Position2D;

/**
 * Represents the view's understanding of an Ellipse.
 */
public class ViewEllipse extends AbstractViewShape {


  /**
   * A constructor that takes in values associated with a regular shape.
   * @param x x position
   * @param y y position
   * @param height height
   * @param width width
   * @param color color
   * @param name name
   */
  public ViewEllipse(int x, int y, int height, int width, Color color, String name) {
    super(x, y, height, width, color, name);
  }

  /**
   * A constuctor that takes inv values from a shapestate and converts it into a ViewShape.
   * @param pos position
   * @param height height
   * @param width width
   * @param color color
   * @param name name
   */
  public ViewEllipse(Position2D pos, int height, int width, Colors color, String name) {
    super(pos, height, width, color, name);
  }

  @Override
  public void render(Graphics2D g) {
    g.setColor(this.color);
    g.fillOval(this.x, this.y, this.width, this.height);
  }

  /**
   * The name of the shape as it is being constructed for the first time.
   * @return the name of the shape with all of its initial values
   */
  @Override
  public String svgName() {
    return String.format("<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\""
            + " fill=\"rgb(%d,%d,%d)\" >"
        ,this.name, this.x, this.y, this.width,
        this.height, this.color.getRed(), this.color.getGreen(), this.color.getBlue());
  }

  /**
   * A move that is formatted to comply with SVG formatting standards.
   * AFAIK there's no way to change all the attributes of an object at once, we need to make sure
   * what's being changed should be changed. In addition, the reason why this isnt in the Moves
   * class is cause we need to differentiate between a rectangle and an ellipse as they have
   * different fields according to SVG formatting.
   * @param initial the initial state of the shape before the move is applied.
   * @param last the state of the shape after its applied
   * @param tickPerSecond the speed of the animation, helps us determine how long each move takes.
   *                      I haven't got the calculation yet since im a bit tired rn.
   * @return the SVG representaion of the move.
   */
  @Override
  public String svgMove(ShapeState initial, ShapeState last, int tickPerSecond) {

    if (initial == null || last == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    if (tickPerSecond < 1) {
      throw new IllegalArgumentException("Illegal tick per second");
    }

    StringBuilder string = new StringBuilder();

    double begin = ((double) initial.getTick() / (double) tickPerSecond) * 1000;
    double duration = ((double) (last.getTick() - initial.getTick()) / (double) tickPerSecond)
        * 1000;

    Colors iColor = initial.getColor();
    Colors fColor = last.getColor();

    if (initial.getPos().getX() != last.getPos().getX()) {
      string.append(String.format(
          "<animate attributeName=\"cx\" attributeType=\"XML\" "
              + "begin=\"%fms\" dur=\"%fms\" fill=\"freeze\" from=\"%d\" to=\"%d\" /> ",
          begin, duration, initial.getPos().getX(), last.getPos().getX())).append("\n");
    }

    if (initial.getPos().getY() != last.getPos().getY()) {
      string.append(String.format(
          "<animate attributeName=\"cy\" attributeType=\"XML\" "
              + "begin=\"%fms\" dur=\"%fms\" fill=\"freeze\" from=\"%d\" to=\"%d\" /> ",
          begin, duration, initial.getPos().getY(), last.getPos().getY())).append("\n");
    }

    if (initial.getWidth() != last.getWidth()) {
      string.append(String.format(
          "<animate attributeName=\"rx\" attributeType=\"XML\" "
              + "begin=\"%fms\" dur=\"%fms\" fill=\"freeze\" from=\"%d\" to=\"%d\" /> ",
          begin, duration, initial.getWidth(), last.getWidth())).append("\n");
    }

    if (initial.getHeight() != last.getHeight()) {
      string.append(String.format(
          "<animate attributeName=\"ry\" attributeType=\"XML\" "
              + "begin=\"%fms\" dur=\"%fms\" fill=\"freeze\" from=\"%d\" to=\"%d\" /> ",
          begin, duration, initial.getWidth(), last.getWidth())).append("\n");
    }

    if (!(iColor.equals(fColor))) {
      string.append(String.format(
          "<animate attributeName=\"fill\" attributeType=\"XML\" "
              + "begin=\"%fms\" dur=\"%fms\" fill=\"freeze\" "
              + "from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\" /> ",
          begin, duration, iColor.getRed(), iColor.getGreen(), iColor.getBlue(),
          fColor.getRed(), fColor.getGreen(), fColor.getBlue())).append("\n");
    }
    return string.toString();
  }

  /**
   * Returns the name of the shape, compliant with SVG formatting. We sorta need this but if there's
   * a better way to implement all this, tell me.
   *
   * @return the type of shape it is in SGV formatting.
   */
  @Override
  public String svgType() {
    return "</ellipse>";
  }

}
