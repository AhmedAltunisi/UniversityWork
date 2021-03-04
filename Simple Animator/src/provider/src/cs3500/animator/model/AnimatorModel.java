package provider.src.cs3500.animator.model;

import java.util.List;

/**
 * The model interface to be implemented by our model implementation.
 *
 * <p>The AnimatorModel implements functions to append motions to a list of Animation objects. These
 * Animation objects store the individual states at animation key frames for a single shape (as a
 * SortedMap between keyframe integer tick values and Shape objects).
 */
public interface AnimatorModel {

  /**
   * Returns the full list of Animations.
   *
   * <p>Each Animation object consists of a single shape, and a list of Operations that will occur
   * one after the other on that shape. An Animation returns a timeline of Shape keyframes as a
   * SortedMap.
   *
   * @return the full list of Animations
   */
  List<Animation> getAllAnimations();

  /**
   * Gets the Animation for the Shape with a given name-tag.
   *
   * @param s the name-tag of the Shape
   * @return the Animation for Shape named s
   */
  Animation getAnimationFor(String s);

  /**
   * Returns the length of time that will need to pass for the final Animation to be finished. This
   * can be established manually within an AnimatorModel or implied by the length of its longest
   * Animation.
   *
   * @return the max time-step as an integer
   */
  int getMaxTimeStep();

  /**
   * Gets the minimum X coordinate of the animation bounding box.
   *
   * @return the minimum X coordinate as an integer
   */
  int getMinX();

  /**
   * Gets the minimum Y coordinate of the animation bounding box.
   *
   * @return the minimum Y coordinate as an integer
   */
  int getMinY();

  /**
   * Gets the width of the animation bounding box.
   *
   * @return the bounding box width as an integer
   */
  int getCanvasWidth();

  /**
   * Gets the height of the animation bounding box.
   *
   * @return the bounding box height as an integer
   */
  int getCanvasHeight();

  /**
   * Add a new shape to the Animator.
   *
   * <p>This requires knowing the initial parameters of the Shape to construct it as well as a tick
   * for it to show up in the animation.
   *
   * @param s the Shape to add
   * @param visibleAt the tick-stamp with which to begin showing the Shape in an animation
   */
  void addNewShape(Shape s, int visibleAt);

  /**
   * Add a new motion (explicit start and end times) for a Shape with the given name-tag.
   *
   * <p>This function is meant to mimic the addMotion function in AnimationBuilder}.
   *
   * @param name the name-tag associated with the Shape with which to apply the motion
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
   * @throws IllegalArgumentException if starting parameters don't equal the expected state of the
   *     Shape at that time-step (no teleporting) or if the move is otherwise invalid
   */
  void addMotionForShape(
      String name,
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
   * Adds an explicit Animation object to the Animator.
   *
   * <p>Since an Animation implementation should always verify continuity
   *
   * @param animation the Animation to add
   * @throws IllegalArgumentException if the length of the Animation exceeds some explicit final
   *     time-step associated with the Animator
   */
  void addAnimation(Animation animation);

  /**
   * Checks if the Animator has a Shape with the given name-tag.
   *
   * @param s the name-tag of the Shape
   * @return if the Shape exists in the Animator
   */
  boolean hasShapeWithName(String s);
}
