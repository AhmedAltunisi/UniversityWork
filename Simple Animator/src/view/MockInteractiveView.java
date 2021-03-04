package view;

import controller.Features;
import java.io.IOException;

/**
 * Class used for testing that the controller successfully calls the view methods.
 */
public class MockInteractiveView implements EasyInteractiveView {

  StringBuilder str;

  /**
   * Constructor for the mock interactive view class which takes in a StringBuilder and appends
   * expected messages to it.
   * @param str StringBuilder to add messages to
   */
  public MockInteractiveView(StringBuilder str) {
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

  /**
   * Allows the user to pause or unpause the animation.
   */
  @Override
  public void pauseUnpause() {
    str.append("pauseUnpause");
  }

  /**
   * When toggled the first time, it allows the user to start the animation while other subsequent
   * toggles allows the animation to restart.
   */
  @Override
  public void startRestart() {
    str.append("startRestart");
  }

  /**
   * Assigns the view a listener.
   *
   * @param features The listener that will command the view.
   */
  @Override
  public void addFeatures(Features features) {
    str.append("addFeatures");
  }

  /**
   * Method used to render an animation model using the constraints of the specific animation view
   * class.
   *
   * @param tickPerSecond Amount of ticks per second used for this rendering of the view
   * @throws IOException If an appendable in an animator class throws an IOException
   */
  @Override
  public void render(int tickPerSecond) throws IOException {
    // Method does not need to be tested for the mock view and does not need to be implemented.
  }
}
