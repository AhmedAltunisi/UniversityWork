package provider.src.cs3500.animator.controller;

import java.awt.event.ActionListener;

/**
 * Interface defining the added functionality we wanted on our controller that wasn't covered by the
 * ActionListener interface.
 */
public interface AnimatorInterface extends ActionListener {

  /**
   * This is the function used when initially calling our animator that takes a string array of
   * arguments and correctly constructs the right view and output.
   *
   * @param args The String array of arguments for constructing the animation
   */
  void handleInput(String[] args);
}
