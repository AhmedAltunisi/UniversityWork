package provider.src.cs3500.animator.model;

import java.util.SortedMap;

/**
 * The Animation class converts a starting Shape to a timeline of Shape states based on input
 * motions. The timeline is stored as a TreeMap between Integer tick-values and Shape objects
 * representing the state at those ticks. The timeline only stores key frames (does not perform
 * state interpolation) so as to minimize unnecessary data storage in the Model.
 */
public interface Animation {

  /**
   * Gets a timeline of the Shape's state as a map of keyframes.
   *
   * <p>Return value is specifically defined as a SortedMap, since the SortedMap orders its entries
   * based on its Key values, and this Map is meant to represent a timeline.
   *
   * @return a SortedMap between key ticks in the timeline and Shape state at those ticks
   */
  SortedMap<Integer, Shape> getShapeTimeline();

  /**
   * Add a state to the Animation timeline based on a given Operation. Operations act incrementally,
   * rather than explicitly, which removes the need for state tracking in animation generation
   * applications (very useful for recursive algorithm visualization).
   *
   * @param op the Operation to apply
   */
  void addOperation(Operation op);

  /**
   * Add a state to the Animation timeline based on starting and ending parameters.
   *
   * <p>This function is meant to mimic the addMotion function in AnimationBuilder}
   *
   * @param t1 initial time
   * @param x1 initial x coordinate
   * @param y1 initial y coordinate
   * @param w1 initial width
   * @param h1 initial height
   * @param r1 initial red
   * @param g1 initial green
   * @param b1 initial blue
   * @param t2 end time
   * @param x2 end x coordinate
   * @param y2 end y coordinate
   * @param w2 end width
   * @param h2 end height
   * @param r2 end red
   * @param g2 end green
   * @param b2 end blue
   * @throws IllegalArgumentException if the starting parameters of this motion do not equal the
   *     ending parameters of the last one (i.e. the last state in the timeline)
   */
  void addExplicitMotion(
      int t1,
      int x1,
      int y1,
      int w1,
      int h1,
      int r1,
      int g1,
      int b1,
      int t2,
      int x2,
      int y2,
      int w2,
      int h2,
      int r2,
      int g2,
      int b2);

  /**
   * Gets the original shape supplied to the Animation.
   *
   * @return the startShape as a Shape
   */
  Shape getStartShape();

  /**
   * Gets the start time for the Animation.
   *
   * @return the startTime as an Integer
   */
  int getStartTick();

  /**
   * Gets the total length of the animation.
   *
   * @return the total number of ticks as an int
   */
  int getLength();
}
