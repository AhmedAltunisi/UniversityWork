package view;

import java.io.IOException;
import model.moves.Moves;
import model.moves.ShapeState;
import model.shapes.Shapes;
import viewmodel.IViewModel;

/**
 * Represents a textual view of the animation.
 */
public class TextView implements EasyAnimatorView {
  private final IViewModel model;
  public Appendable ap;

  /**
   * Constructor for a textview which receives a model as input.
   * @param model Model used with this view
   */
  public TextView(IViewModel model, Appendable ap) throws IllegalArgumentException {
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

    this.ap.append(String.format("canvas %d %d %d %d", model.getX(), model.getY(),
        model.getWidth(), model.getHeight())).append("\n");

    for (Shapes shape : model.getShapesAndMoves().keySet()) {
      ap.append(shape.toString()).append("\n");

      int lastMoveFinalTick = 0;
      int nextMoveInitTick = 0;

      for (Moves move : model.getShapesAndMoves().get(shape)) {
        nextMoveInitTick = move.getInitialState().getTick();
        if (nextMoveInitTick != lastMoveFinalTick) {
          double time1 = ((double) lastMoveFinalTick / (double) tickPerSecond);
          double time2 = ((double) nextMoveInitTick / (double) tickPerSecond);
          ap.append("motion ").append(shape.getName()).append(" ").append(Integer.toString((int)
              time1)).append(" ")
              .append(move.getInitialState().toString()).append(" ").append(Integer.toString((int)
              time2)).append(" ")
              .append(move.getFinalState().toString()).append("\n");
        }

        ShapeState initialState = move.getInitialState();
        ShapeState finalState = move.getFinalState();

        double time1 = ((double) initialState.getTick() / (double) tickPerSecond);
        double time2 = ((double) finalState.getTick() / (double) tickPerSecond);


        ap.append("motion ").append(shape.getName()).append(" "
            + "").append(Integer.toString((int)time1)).append(" ")
            .append(initialState.toString()).append(""
            + " ").append(Integer.toString((int) time2)).append(" ")
            .append(finalState.toString()).append("\n");
        lastMoveFinalTick = move.getFinalState().getTick();
      }
    }
  }
}
