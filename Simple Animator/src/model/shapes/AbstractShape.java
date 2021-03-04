package model.shapes;


/**
 * Class that is an abstract shape that contains values and methods that all shapes will have.
 * Allows for construction of a shape from any class that extends it.
 */
public abstract class AbstractShape implements Shapes {

  protected String name;

  /**
   * Constructor that allows creation of a shape utilizing rgb integer values for creation.
   *
   * @param name   Name of shape
   * @throws IllegalArgumentException If any of the values are inputted into the constructor are
   *                                  invalid
   */
  protected AbstractShape(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

}
