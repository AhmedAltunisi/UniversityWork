package model;

import animator.util.AnimationBuilder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import model.moves.AllMove;
import model.moves.Moves;
import model.moves.ShapeState;
import model.moves.Teleport;
import model.shapes.Ellipse;
import model.shapes.Rectangle;
import model.shapes.Shapes;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a model for a basic animator. It cannot rewind animations so inputting ticks that are
 * less than the tick that has been processed would result in an error.
 */
public class BasicAnimatorModel implements EasyAnimatorModel {

  private final Map<Shapes, List<Moves>> shapesAndMoves;

  private int height;

  private int width;

  private int leftMostX;

  private int topMostY;


  /**
   * Constructs a version of the basic animator model. It allows users to start their animation
   * without needing to input values into the constructor.
   */


  private BasicAnimatorModel(Builder builder) {
    this.shapesAndMoves = new LinkedHashMap<>();
    this.setCanvas(builder.height, builder.width, builder.x, builder.y);
    for (Shapes shape : builder.shapesAndMoves.keySet()) {
      this.addShapes(shape);
      for (Moves move : builder.shapesAndMoves.get(shape)) {
        this.addMoves(move, shape);
      }
    }
    this.checkEndpoints();
  }

  /**
   * Represents a builder that builds the animation.
   */
  public static final class Builder implements AnimationBuilder<EasyAnimatorModel> {


    private final LinkedHashMap<Shapes, List<Moves>> shapesAndMoves;
    private int height;
    private int width;
    private int x;
    private int y;

    /**
     * A constructor, it acts as a tool to create the model.
     */
    public Builder() {
      this.shapesAndMoves = new LinkedHashMap<>();
      this.height = 0;
      this.width = 0;
      this.x = 0;
      this.y = 0;
    }

    /**
     * Constructs a final document.
     *
     * @return the newly constructed document
     */
    @Override
    public EasyAnimatorModel build() {
      return new BasicAnimatorModel(this);
    }

    /**
     * Specify the bounding box to be used for the animation.
     *
     * @param x      The leftmost x value
     * @param y      The topmost y value
     * @param width  The width of the bounding box
     * @param height The height of the bounding box
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<EasyAnimatorModel> setBounds(int x, int y, int width, int height) {

      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      return this;
    }

    /**
     * Adds a new shape to the growing document.
     *
     * @param name The unique name of the shape to be added. No shape with this name should already
     *             exist.
     * @param type The type of shape (e.g. "ellipse", "rectangle") to be added. The set of supported
     *             shapes is unspecified, but should include "ellipse" and "rectangle" as a
     *             minimum.
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<EasyAnimatorModel> declareShape(String name, String type) {
      switch (type) {
        case "rectangle":
          this.shapesAndMoves.put(new Rectangle(name), new ArrayList<>());
          break;
        case "ellipse":
          this.shapesAndMoves.put(new Ellipse(name), new ArrayList<>());
          break;
        default:
          throw new IllegalArgumentException("Shape not supported");
      }
      return this;
    }

    /**
     * Adds a transformation to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t1   The start time of this transformation
     * @param x1   The initial x-position of the shape
     * @param y1   The initial y-position of the shape
     * @param w1   The initial width of the shape
     * @param h1   The initial height of the shape
     * @param r1   The initial red color-value of the shape
     * @param g1   The initial green color-value of the shape
     * @param b1   The initial blue color-value of the shape
     * @param t2   The end time of this transformation
     * @param x2   The final x-position of the shape
     * @param y2   The final y-position of the shape
     * @param w2   The final width of the shape
     * @param h2   The final height of the shape
     * @param r2   The final red color-value of the shape
     * @param g2   The final green color-value of the shape
     * @param b2   The final blue color-value of the shape
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<EasyAnimatorModel> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2) {

      if (t1 > t2) {
        throw new IllegalArgumentException("Initial time is supposed to come before final time");
      }

      ShapeState initial = new ShapeState(t1, x1, y1, h1, w1, r1, g1, b1);
      ShapeState last = new ShapeState(t2, x2, y2, h2, w2, r2, g2, b2);
      for (Shapes shape : this.shapesAndMoves.keySet()) {
        if (shape.getName().equals(name)) {
          List<Moves> moves = this.shapesAndMoves.get(shape);
          if (t2 == t1 + 1) {
            moves.add(new Teleport(initial, last));
          } else {
            moves.add(new AllMove(initial, last));
          }
          this.shapesAndMoves.put(shape, moves);
        }
      }
      return this;
    }
  }


  private void checkOverlap(Moves move, List<Moves> moves) {
    for (Moves tempMove : moves) {
      if (move.equals(tempMove)) {
        throw new IllegalArgumentException("Overlap of moves trying to add");
      }
    }
  }

  //Assuming already sorted
  private void addSorted(Moves move, List<Moves> moves) {
    for (Moves tempMove : moves) {
      if (move.getInitialState().getTick() < tempMove.getInitialState().getTick()) {
        moves.add(0, move);
        return;
      }
    }
    moves.add(move);
  }

  private void checkEndpoints() {
    for (Shapes shape : this.getShapesAndMoves().keySet()) {
      List<Moves> moves = this.getShapesAndMoves().get(shape);
      for (int i = 0; i < moves.size() - 1; i++) {
        ShapeState beginMoveFinal = moves.get(i).getFinalState();
        ShapeState nextMoveInit = moves.get(i + 1).getInitialState();
        if (!beginMoveFinal.getColor().equals(nextMoveInit.getColor())
            || beginMoveFinal.getHeight() != nextMoveInit.getHeight()
            || beginMoveFinal.getWidth() != nextMoveInit.getWidth()
            || !beginMoveFinal.getPos().equals(nextMoveInit.getPos())) {
          throw new IllegalArgumentException("Endpoints in moves do not align");
        }
      }
    }

  }

  @Override
  public void setCanvas(int height, int width, int x, int y) {

    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Measurements cannot be negative");
    }
    this.height = height;
    this.width = width;
    this.leftMostX = x;
    this.topMostY = y;
  }

  @Override
  public int getX() {
    return this.leftMostX;
  }

  @Override
  public int getY() {
    return this.topMostY;
  }

  /**
   * Removes a shape from the animation.
   *
   * @param shape the shape to be removed
   */
  @Override
  public void removeShape(Shapes shape) {

    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }
    if (this.shapesAndMoves.containsKey(shape)) {
      this.shapesAndMoves.remove(shape);
    } else {
      throw new IllegalArgumentException("Shape is not in the animation");
    }
  }

  /**
   * Removes a move from the animation.
   *
   * @param move  the move to be removed.
   * @param shape the shape associated with the move.
   */
  @Override
  public void removeMove(Moves move, Shapes shape) {

    if (move == null || shape == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    if (this.shapesAndMoves.containsKey(shape)) {
      List<Moves> moves = this.shapesAndMoves.get(shape);

      if (moves.contains(move)) {
        moves.remove(move);
        this.shapesAndMoves.replace(shape, moves);
      } else {
        throw new IllegalArgumentException("Move is not associated with shape");
      }
    } else {
      throw new IllegalArgumentException("Shape is not in the animation");
    }
  }

  @Override
  public Shapes getShape(String name) {

    Shapes result = null;
    for (Shapes s : this.shapesAndMoves.keySet()) {
      if (s.getName().equals(name)) {
        result = s;
        break;
      }
    }
    Objects.requireNonNull(result);
    return result;
  }

  /**
   * Adds a move to the animation.
   *
   * @param move The move to be added.
   */
  @Override
  public void addMoves(Moves move, Shapes shape) throws IllegalArgumentException {

    if (move == null) {
      throw new IllegalArgumentException("Move cannot be null");
    }
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }

    List<Moves> moves = this.shapesAndMoves.get(shape);

    if (moves == null) {
      throw new IllegalArgumentException("Shape does not exist");
    }

    if (moves.size() == 0) {
      moves.add(move);
    } else {
      this.checkOverlap(move, moves);
      this.addSorted(move, moves);
    }
  }

  private void checkShapeValidity(Shapes shape) {

    if (shape == null) {
      throw new IllegalArgumentException("shape cannot be null");
    }
    for (Shapes inShape : this.shapesAndMoves.keySet()) {
      if (shape.getName().equals(inShape.getName())) {
        throw new IllegalArgumentException("Shape's name is already taken");
      }
    }
  }

  @Override
  public void addShapes(Shapes shape) {
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null");
    }
    this.checkShapeValidity(shape);
    this.shapesAndMoves.put(shape, new ArrayList<>());
  }

  /**
   * Obtains the list of moves.
   *
   * @return the list of moves.
   */
  @Override
  public Map<Shapes, List<Moves>> getShapesAndMoves() {
    return new LinkedHashMap<>(this.shapesAndMoves);
  }

  @Override
  public int getLastTick() {
    int lastTick = 0;

    for (Shapes shape : this.getShapesAndMoves().keySet()) {
      List<Moves> shapeMoves = this.getShapesAndMoves().get(shape);
      int finalTick = shapeMoves.get(shapeMoves.size() - 1).getFinalState().getTick();
      if (finalTick > lastTick) {
        lastTick = finalTick;
      }
    }
    return lastTick;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

}
