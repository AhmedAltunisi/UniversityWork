package view;

import java.awt.Graphics2D;
import model.moves.ShapeState;

/**
 * Represents the view's version of shapes.
 */
public interface ViewShapes {

  /**
   * Renders the shape as intended.
   * @param g the graphics
   */
  void render(Graphics2D g);

  String getName();

  /**
   * The name of the shape as it is being constructed for the first time.
   * @return the name of the shape with all of its initial values
   */
  String svgName();

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
  String svgMove(ShapeState initial, ShapeState last, int tickPerSecond);

  /**
   * Returns the name of the shape, compliant with SVG formatting.
   * We sorta need this but if there's a better way to implement all this, tell me.
   * @return the type of shape it is in SGV formatting.
   */
  String svgType();


}
