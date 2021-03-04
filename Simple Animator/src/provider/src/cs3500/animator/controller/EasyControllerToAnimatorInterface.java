package provider.src.cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.util.Objects;
import provider.src.cs3500.animator.view.AnimatorView;

/**
 * Class that implements the controller that the providers gave us.
 */
public class EasyControllerToAnimatorInterface implements AnimatorInterface {

  AnimatorView view;

  /**
   * Constructor that takes in the view.
   *
   * @param view View that will display the controller's functionality
   */
  public EasyControllerToAnimatorInterface(AnimatorView view) {

    this.view = Objects.requireNonNull(view);


  }

  /**
   * This is the function used when initially calling our animator that takes a string array of
   * arguments and correctly constructs the right view and output.
   *
   * @param args The String array of arguments for constructing the animation
   */
  @Override
  public void handleInput(String[] args) {
    throw new UnsupportedOperationException("Unsupported functionality: handleInput");
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Start Button":
        view.toggleStart();
        break;
      case "Pause Button":
        view.togglePause();
        break;
      case "Faster Button":
        view.increaseSpeed();
        break;
      case "Slower Button":
        view.decreaseSpeed();
        break;
      case "Loop Button":
        view.toggleLoop();
        break;
      case "Restart Button":
        view.restartAnimation();
        break;
      default:
        break;
    }
  }

}
