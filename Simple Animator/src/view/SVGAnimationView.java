package view;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.moves.Moves;
import model.shapes.Shapes;
import viewmodel.IViewModel;

/**
 * Represents an SVG representation of an animation. It produces text in XML format and is compliant
 * with the SVG file format.
 */
public class SVGAnimationView implements EasyAnimatorView {


  private final IViewModel model;
  public Appendable ap;

  /**
   * A constructor for the view.
   *
   * @param model An Easy Animator Model, enforces the rules of the animation.
   * @param ap    An appendable, allows for producing the text in any way needed
   */
  public SVGAnimationView(IViewModel model, Appendable ap) throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    if (ap == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    this.model = model;
    this.ap = ap;


  }


  @Override
  public void render(int tickPerSecond) throws IOException {

    this.ap.append(String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\"\n"
            + "     xmlns=\"http://www.w3.org/2000/svg\">", model.getWidth(),
        model.getHeight())).append("\n");

    Map<Shapes, List<Moves>> map = model.getShapesAndMoves();

    for (Shapes shape : map.keySet()) {

      for (int i = 0; i < model.getShapesAndMoves().get(shape).size(); i++) {
        Moves move = model.getShapesAndMoves().get(shape).get(i);
        ViewShapes vShape = shape.makeViewShape(move.getInitialState());

        if (model.getShapesAndMoves().get(shape).size() - 1 == i && i == 0) {
          ap.append(vShape.svgName()).append("\n")
              .append(vShape.svgMove(move.getInitialState(),
                  move.getFinalState(), tickPerSecond)).append("\n").
              append(vShape.svgType()).append("\n");
        } else {
          if (i == 0) {
            ap.append(vShape.svgName()).append("\n")
                .append(vShape.svgMove(move.getInitialState(),
                    move.getFinalState(), tickPerSecond)).append("\n");
          } else {
            if (model.getShapesAndMoves().get(shape).size() - 1 == i) {
              ap.append(vShape.svgMove(move.getInitialState(), move.getFinalState(),
                  tickPerSecond)).append("\n").append(vShape.svgType()).append("\n");
            } else {
              ap.append(vShape.svgMove(move.getInitialState(), move.getFinalState(),
                  tickPerSecond)).append("\n");
            }
          }
        }
      }
    }
    this.ap.append("</svg>");
  }

}


