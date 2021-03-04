package provider.src.cs3500.animator.view;

import provider.src.cs3500.animator.model.Animation;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The animator view interface to be extended.
 *
 * <p>An AnimatorView only has to implement a single function, render, for most implementations.
 * This function takes the list of Animations from the model/controller, a tick per second, and
 * model canvas information.
 *
 * <p>The other functions in the AnimatorView interface are included to allow for interactive view
 * implementations. If a view is not user-interactive, these functions can be filled with an
 * UnsupportedOperationException.
 */
public interface AnimatorView {

  /**
   * Renders an Animation for the user.
   *
   * <p>Produces the output expected by the particular view implementation. This could amount to
   * writing to an Appendable, displaying a GUI, etc.
   *
   * @param animations List of the Aniamtions (shape timelines) to be shown in the view
   * @param tickPerSecond a frame-rate with which to run the animation
   * @param canvasWidth The width of the model canvas
   * @param canvasHeight The height of the model canvas
   * @param xOffset The minimum x value of the model canvas
   * @param yOffset The minimum y value of the model canvas
   */
  void render(
      List<Animation> animations,
      int tickPerSecond,
      int canvasWidth,
      int canvasHeight,
      int xOffset,
      int yOffset);

  /**
   * This is a function used specifically in the interactive view that allows us to set the listener
   * for the buttons as the valid listener passed.
   *
   * @param listener The valid listener that we can use for our button callbacks
   */
  void setListener(ActionListener listener);

  /**
   * This function is used specifically in the Interactive view. This allows for the external
   * modification of the animation that starts it running either after being paused or for the first
   * time.
   */
  void toggleStart();

  /**
   * This function is used specifically in the Interactive view. This allows for the external
   * modification of the animation that pauses it.
   */
  void togglePause();

  /**
   * This function is used specifically in the Interactive view. This allows for the external
   * modification of the animation that restarts it while its running.
   */
  void restartAnimation();

  /**
   * This function is used specifically in the Interactive view. This allows for the external
   * modification of the animation that toggles whether or not the animation loops after it ends.
   */
  void toggleLoop();

  /**
   * This function is used specifically in the Interactive view. This allows for the external
   * modification of the animation that increases the playback speed of the animation.
   */
  void increaseSpeed();

  /**
   * This function is used specifically in the Interactive view. This allows for the external
   * modification of the animation that decreases the playback speed of the animation.
   */
  void decreaseSpeed();
}
