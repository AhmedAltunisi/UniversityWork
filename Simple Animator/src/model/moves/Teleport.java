package model.moves;

import java.util.Objects;

/**
 * Represents a shape's ability to teleport. It can move from one place to another within the blink
 * of an eye.
 */
public class Teleport implements Moves {

  private final ShapeState initState;
  private final ShapeState finalState;

  /**
   * A constructor, it generates the move.
   * @param initState the initial state of the shape.
   * @param finalState the final state of the shape.
   */
  public Teleport(ShapeState initState, ShapeState finalState) {

    if (initState == null || finalState == null) {
      throw new IllegalArgumentException("ShapeState cannot be null");
    }

    if (initState.getTick() > finalState.getTick()) {
      throw new IllegalArgumentException("Initial State comes after final state");
    }

    if (initState.getTick() + 1 != finalState.getTick()) {
      throw new IllegalArgumentException("Move must be a teleport");
    }

    this.initState = initState;
    this.finalState = finalState;
  }

  @Override
  public ShapeState apply(int tick) {
    if (tick <= this.initState.getTick()) {
      return new ShapeState(this.initState);
    } else {
      return new ShapeState(this.finalState);
    }
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

    return tick <= this.initState.getTick() || tick > this.finalState.getTick();
  }


  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Teleport)) {
      return false;
    }

    Teleport that = (Teleport) other;

    return this.getInitialState().getTick() == that.getInitialState().getTick()
        && this.getFinalState().getTick() == that.getFinalState().getTick();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.initState, this.finalState);
  }

  @Override
  public String toString() {
    return String.format("%d %d %d %d %d %d %d %d   %d %d %d %d %d %d %d %d\n",
        this.initState.getTick(), this.initState.getPos().getX(), this.initState.getPos().getY(),
        this.initState.getWidth(), this.initState.getHeight(), this.initState.getColor().getRed(),
        this.initState.getColor().getBlue(), this.initState.getColor().getGreen(),
        this.finalState.getTick(), this.finalState.getPos().getX(),
        this.finalState.getPos().getY(),
        this.finalState.getWidth(), this.finalState.getHeight(),
        this.finalState.getColor().getRed(),
        this.finalState.getColor().getBlue(), this.finalState.getColor().getGreen());
  }
}
