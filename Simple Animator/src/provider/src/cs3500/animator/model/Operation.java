package provider.src.cs3500.animator.model;

/**
 * The Operation represents an incremental motion to be performed on a Shape.
 *
 * <p>It will store values associated with increments in position, scale, and color values.
 *
 * <p>It implements a function which applies this Operation to a given Shape. The only other
 * function it implements is an accessor for the tick-length of the Operation, since all other data
 * is meant not to be accessed directly but rather utilized in the apply function.
 *
 * <p>The incremental nature of an Operation removes the need for state tracking in animation
 * generators, which is very useful for visualizing algorithms (especially recursive ones).
 */
public interface  Operation {

  /**
   * The number of animation ticks associated with the Operation.
   *
   * @return The length of the Operation as an integer count of animation ticks.
   */
  int getTicks();

  /**
   * Takes a Shape as an input and returns a new Shape that represents.
   *
   * @param s The shape the operation is being done on
   * @return The new, modified shape.
   */
  Shape apply(Shape s);
}
