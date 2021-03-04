package controller;

import java.util.Objects;
import view.EasyInteractiveView;

/**
 * Represents the controller used in our interactive model.
 */
public class InteractiveViewController implements Features {


  private final EasyInteractiveView view;

  /**
   * A constructor for our controller, it takes in a view so that it acts as its listener.
   * @param view takes in a view to control.
   */
  public InteractiveViewController(EasyInteractiveView view) {
    this.view = Objects.requireNonNull(view);

    this.view.addFeatures(this);

  }


  @Override
  public void toggleLoop() {
    view.toggleLoop();
  }

  @Override
  public void toggleStart() {
    view.startRestart();
  }

  @Override
  public void pauseUnpause() {
    view.pauseUnpause();
  }

  @Override
  public void speedUp() {
    view.speedUp();
  }

  @Override
  public void slowDown() {
    view.slowDown();
  }
}
