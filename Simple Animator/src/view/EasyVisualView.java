package view;

import viewmodel.IViewModel;

/**
 * Represents an interface for Visual Views. Contains all the usual functionality
 * of the EasyAnimatorView but allows for update the view utilizing a currentTick
 * such that the view keeps track of running time.
 */
public interface EasyVisualView extends EasyAnimatorView {

  /**
   * Updates the data found within the view given a panel to draw the animation on and a tick.
   * @param panel the panel that will be used to draw the animation on.
   * @param currentTick the current tick of the animation.
   */
  void updateView(DrawPanel panel, int currentTick);

  /**
   * Gets the final tick of the animation.
   * @return the final tick
   */
  int getFinalTick();

  /**
   * Returns the IViewModel used for the animation.
   * @return the IViewModel
   */
  IViewModel getModel();

}
