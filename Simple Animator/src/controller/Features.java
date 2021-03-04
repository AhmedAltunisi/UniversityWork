package controller;

/**
 * Interface for controller usage for the views for this project. Contains methods that are called
 * when an animation needs features such as toggling looping, toggling starting, pausing and
 * unpausing, and speeding up or slowing down.
 */
public interface Features {

  /**
   * Allows the animation to loop or to stop looping.
   */
  void toggleLoop();

  /**
   * When toggled the first time, it allows the user to start the animation while other subsequent
   * toggles allows the animation to restart.
   */
  void toggleStart();

  /**
   * Allows the user to pause or unpause the animation.
   */
  void pauseUnpause();

  /**
   * Allows the user to speed up the animation.
   */
  void speedUp();

  /**
   * Allows the user to slow down the animation.
   */
  void slowDown();
}
