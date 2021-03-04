package provider.src.cs3500.animator.model;

import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import model.moves.Moves;
import model.moves.ShapeState;
import model.shapes.Colors;
import model.shapes.Shapes;

/**
 * Class that is used to convert our implementation of a the shapes which a map from shapes to moves
 * to what the providers use which is an animation which is a mapping of each tick to the shapes
 * state.
 */
public class ShapesAndMovesToAnimation implements Animation {

  private final SortedMap<Integer, Shape> animation;
  private final Shape shape;

  /**
   * Constructor that converts shape and its list of moves to an animation.
   * @param shape Shapes instance of our shape
   * @param moves Moves instance of our moves
   */
  public ShapesAndMovesToAnimation(Shapes shape, List<Moves> moves) {

    Objects.requireNonNull(shape);
    Objects.requireNonNull(moves);
    this.animation = new TreeMap<>();

    Shape s = new ShapesToShape(shape);
    for (Moves m : moves) {
      ShapeState one = m.getInitialState();
      ShapeState two = m.getFinalState();
      int t1 = one.getTick();
      int t2 = two.getTick();
      Position2D posn1 = new Position2D(one.getPos().getX(), one.getPos().getY());
      Position2D posn2 = new Position2D(two.getPos().getX(), two.getPos().getY());
      Position2D scale1 = new Position2D(one.getWidth(), one.getHeight());
      Position2D scale2 = new Position2D(two.getWidth(), two.getHeight());
      Colors c1 = new Colors(one.getColor());
      Colors c2 = new Colors(two.getColor());

      Shape s1 = new BasicShape(s.getName(), s.getType(), posn1, scale1,
          new Color(c1.getRed(), c1.getGreen(), c1.getBlue()));
      Shape s2 = new BasicShape(s.getName(), s.getType(), posn2, scale2,
          new Color(c2.getRed(), c2.getGreen(), c2.getBlue()));

      this.animation.put(t1, s1);
      this.animation.put(t2, s2);
    }

    this.shape = s;
  }

  /**
   * Gets a timeline of the Shape's state as a map of keyframes.
   *
   * <p>Return value is specifically defined as a SortedMap, since the SortedMap orders its entries
   * based on its Key values, and this Map is meant to represent a timeline.
   *
   * @return a SortedMap between key ticks in the timeline and Shape state at those ticks
   */
  @Override
  public SortedMap<Integer, Shape> getShapeTimeline() {
    return this.animation;
  }

  /**
   * Add a state to the Animation timeline based on a given Operation. Operations act incrementally,
   * rather than explicitly, which removes the need for state tracking in animation generation
   * applications (very useful for recursive algorithm visualization).
   *
   * @param op the Operation to apply
   */
  @Override
  public void addOperation(Operation op) {

    if (op.getTicks() != animation.lastKey() || animation.containsKey(op.getTicks())) {
      throw new IllegalArgumentException("something");
    }

    this.animation.put(op.getTicks(), op.apply(shape));
  }

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
   *                                  ending parameters of the last one (i.e. the last state in the
   *                                  timeline)
   */
  @Override
  public void addExplicitMotion(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {

    if (t2 < t1) {
      throw new IllegalArgumentException("move is illegal");
    }
    try {
      Shape s = Objects.requireNonNull(animation.get(t1));
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("End points need to match up");
    }

    Position2D pos1 = shape.getPosition();
    Position2D pos2 = new Position2D(x1, y1);
    Position2D scale = shape.getScale();
    Position2D scale2 = new Position2D(w1, h1);
    Color color = shape.getColor();
    Color color2 = new Color(r1, g1, b1);

    if (pos1.equals(pos2) && scale.equals(scale2) && color.equals(color2)) {

      Shape s2 = new BasicShape(shape.getName(), shape.getType(),
          new Position2D(x2, y2), new Position2D(w2, h2), new Color(r2, g2, b2));
    } else {
      throw new IllegalArgumentException("endpoints do not match up");
    }


  }

  /**
   * Gets the original shape supplied to the Animation.
   *
   * @return the startShape as a Shape
   */
  @Override
  public Shape getStartShape() {
    return animation.get(animation.firstKey());
  }

  /**
   * Gets the start time for the Animation.
   *
   * @return the startTime as an Integer
   */
  @Override
  public int getStartTick() {
    return animation.firstKey();
  }

  /**
   * Gets the total length of the animation.
   *
   * @return the total number of ticks as an int
   */
  @Override
  public int getLength() {
    return animation.lastKey();
  }
}
