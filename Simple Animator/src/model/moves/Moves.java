package model.moves;


/**
 * Represents an interface for moves that can be executed by the Animator model.
 */
public interface Moves {

  /**
   * Applies the move onto a shape when it is on the correct tick.
   *
   * @return the shape with the move applied onto it
   */
  ShapeState apply(int tick);


  /**
   * Gets the initial tick of which the move is applicable on.
   *
   * @return the initial tick.
   */
  ShapeState getInitialState();

  /**
   * Gets the final tick of which the move is applicable on.
   *
   * @return the final tick.
   */
  ShapeState getFinalState();

  /**
   * Checks whether the move is finished at a certain time.
   *
   * @param tick the time to be checked.
   * @return whether the move is finished at that time.
   *
   */
  boolean isMoveFinished(int tick);



}
