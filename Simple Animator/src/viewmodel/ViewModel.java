package viewmodel;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.EasyAnimatorModel;
import model.moves.Moves;
import model.shapes.Shapes;

/**
 * Represents a model that can only access information found within it and cannot update it.
 */
public class ViewModel implements IViewModel {

  private final EasyAnimatorModel model;


  /**
   * A constructor, it allows for the creation of a view model, only when given a  model.
   * @param model The model to be accessed.
   */
  public ViewModel(EasyAnimatorModel model) {

    this.model = Objects.requireNonNull(model);
  }


  /**
   * Obtains the hashmap of shapes and it's list of moves.
   *
   * @return the hashmap of shapes and it's list of moves.
   */
  @Override
  public Map<Shapes, List<Moves>> getShapesAndMoves() {
    return this.model.getShapesAndMoves();
  }

  /**
   * Returns the last tick in the animation aka at what tick the animation will end on.
   *
   * @return the last tick.
   */
  @Override
  public int getLastTick() {
    return this.model.getLastTick();
  }

  /**
   * The height of the canvas.
   *
   * @return the height of the canvas.
   */
  @Override
  public int getHeight() {
    return this.model.getHeight();
  }

  /**
   * The width of the canvas.
   *
   * @return the width of the canvas.
   */
  @Override
  public int getWidth() {
    return this.model.getWidth();
  }

  /**
   * The leftmost x value of the canvas.
   *
   * @return the leftmost x value of the canvas.
   */
  @Override
  public int getX() {
    return this.model.getX();
  }

  /**
   * The topmost y value of the canvas.
   *
   * @return the topmost y value of the canvas.
   */
  @Override
  public int getY() {
    return this.model.getY();
  }
}
