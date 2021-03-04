package controller;

/**
 * Mock controller class used for testing that Features implementations actually execute
 * their method bodies when called.
 */
public class MockController implements Features {

  private StringBuilder str;

  /**
   * Contructor for the mock controller that takes a string builder to append test messages to.
   * @param str String builder to append values to
   */
  public MockController(StringBuilder str) {
    this.str = str;
  }

  /**
   * Allows the animation to loop or to stop looping.
   */
  @Override
  public void toggleLoop() {
    str.append("toggleLoop");
  }

  /**
   * When toggled the first time, it allows the user to start the animation while other subsequent
   * toggles allows the animation to restart.
   */
  @Override
  public void toggleStart() {
    str.append("toggleStart");
  }

  /**
   * Allows the user to pause or unpause the animation.
   */
  @Override
  public void pauseUnpause() {
    str.append("pauseUnpause");
  }

  /**
   * Allows the user to speed up the animation.
   */
  @Override
  public void speedUp() {
    str.append("speedUp");
  }

  /**
   * Allows the user to slow down the animation.
   */
  @Override
  public void slowDown() {
    str.append("slowDown");
  }
}
