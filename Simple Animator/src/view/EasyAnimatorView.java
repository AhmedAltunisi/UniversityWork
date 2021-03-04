package view;


import java.io.IOException;

/**
 * Represent an interface for the view of the animator. While currently not harboring any methods,
 * this interface will soon grow to actually output how these animations would look like.
 */
public interface EasyAnimatorView {

  /**
   * Method used to render an animation model using the constraints of the specific animation
   * view class.
   * @param tickPerSecond Amount of ticks per second used for this rendering of the view
   * @throws IOException If an appendable in an animator class throws an IOException
   */
  void render(int tickPerSecond) throws IOException;


}
