package model.moves;

import java.util.Objects;

/**
 * Represents a move that has the ability to change all the values of a shape.
 */
public class AllMove implements Moves {

  private final ShapeState initState;
  private final ShapeState finalState;

  /**
   * A constructor, it generates the move.
   * @param initState the initial state of the shape.
   * @param finalState the final state of the shape.
   */
  public AllMove(ShapeState initState, ShapeState finalState) {

    if (initState == null || finalState == null) {
      throw new IllegalArgumentException("ShapeState cannot be null");
    }

    if (initState.getTick() > finalState.getTick()) {
      throw new IllegalArgumentException("Initial State comes after final state");
    }
    if (initState.getTick() + 1 == finalState.getTick()) {
      throw new IllegalArgumentException("Teleport move must be explicitly stated");
    }
    this.initState = initState;
    this.finalState = finalState;
  }

  @Override
  public ShapeState apply(int tick) {

    ShapeState init = this.initState;
    ShapeState f = this.finalState;
    int x = this.tweening(init.getPos().getX(), f.getPos().getX(), tick);
    int y = this.tweening(init.getPos().getY(), f.getPos().getY(), tick);
    int height = this.tweening(init.getHeight(), f.getHeight(), tick);
    int width = this.tweening(init.getWidth(), f.getWidth(), tick);
    int r = this.tweening(init.getColor().getRed(), f.getColor().getRed(), tick);
    int g = this.tweening(init.getColor().getGreen(), f.getColor().getGreen(), tick);
    int b = this.tweening(init.getColor().getBlue(), f.getColor().getBlue(), tick);
    return new ShapeState(tick, x, y, height, width, r, g, b);
  }

  private int tweening(int a, int b, int currentTick) {
    double initialTick = this.initState.getTick();
    double finalTick = this.finalState.getTick();

    return (int) (((double) a * ((finalTick - currentTick) / (finalTick - initialTick)))
        + ((double) b * ((currentTick - initialTick) / (finalTick - initialTick))));
  }

  @Override
  public ShapeState getInitialState() {
    return new ShapeState(this.initState);
  }

  @Override
  public ShapeState getFinalState() {
    return new ShapeState(this.finalState);
  }

  /**
   * Checks whether the move is finished at a certain time.
   *
   * @param tick the time to be checked.
   * @return whether the move is finished at that time.
   */
  @Override
  public boolean isMoveFinished(int tick) {
    return this.initState.getTick() > tick
        || this.finalState.getTick() < tick;
  }


  @Override
  public boolean equals(Object other) {
    if (!(other instanceof AllMove)) {
      return false;
    }

    AllMove that = (AllMove) other;

    if ((this.getInitialState().getTick() == this.getFinalState().getTick()
        && this.getInitialState().getTick() == that.getInitialState().getTick()
        && this.getFinalState().getTick() != that.getFinalState().getTick())
        || (that.getInitialState().getTick() == that.getFinalState().getTick()
        && this.getInitialState().getTick() == that.getInitialState().getTick()
        && this.getFinalState().getTick() != that.getFinalState().getTick())) {
      return false;
    } else {
      return ((this.getInitialState().getTick() >= that.getInitialState().getTick()
          && this.getInitialState().getTick() < that.getFinalState().getTick())
          || (that.getInitialState().getTick() >= this.getInitialState().getTick()
          && that.getInitialState().getTick() < this.getFinalState().getTick()));
    }

  }

  @Override
  public int hashCode() {
    return Objects.hash(initState, finalState);
  }

  @Override
  public String toString() {
    return String.format("%d %d %d %d %d %d %d %d   %d %d %d %d %d %d %d %d\n",
        this.initState.getTick(), this.initState.getPos().getX(),
        this.initState.getPos().getY(),
        this.initState.getWidth(), this.initState.getHeight(),
        this.initState.getColor().getRed(),
        this.initState.getColor().getBlue(), this.initState.getColor().getGreen(),
        this.finalState.getTick(), this.finalState.getPos().getX(),
        this.finalState.getPos().getY(),
        this.finalState.getWidth(), this.finalState.getHeight(),
        this.finalState.getColor().getRed(),
        this.finalState.getColor().getBlue(),
        this.finalState.getColor().getGreen());
  }
}
