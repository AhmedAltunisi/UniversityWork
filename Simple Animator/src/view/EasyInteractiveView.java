package view;

import controller.Features;

/**
 * Represents the interface for an interactive view. Has the same functionality as a normal
 * EasyAnimatorView but adds additional functionality that allow for controlling some of how the
 * animation plays out.
 */
public interface EasyInteractiveView extends EasyAnimatorView {


  /**
   * Allows the animation to loop or to stop looping.
   */
  void toggleLoop();

  /**
   * Allows the user to speed up the animation.
   */
  void speedUp();

  /**
   * Allows the user to slow down the animation.
   */
  void slowDown();

  /**
   * Allows the user to pause or unpause the animation.
   */
  void pauseUnpause();

  /**
   * When toggled the first time, it allows the user to start the animation while other subsequent
   * toggles allows the animation to restart.
   */
  void startRestart();

  /**
   * Assigns the view a listener.
   * @param features The listener that will command the view.
   */
  void addFeatures(Features features);
}
